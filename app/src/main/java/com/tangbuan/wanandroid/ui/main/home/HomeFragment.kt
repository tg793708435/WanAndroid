package com.tangbuan.wanandroid.ui.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tangbuan.wanandroid.BR
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.base.App
import com.tangbuan.wanandroid.base.BaseFragment
import com.tangbuan.wanandroid.data.bean.Article
import com.tangbuan.wanandroid.data.bean.Banner
import com.tangbuan.wanandroid.data.bean.CollectData
import com.tangbuan.wanandroid.data.bean.PageResponse
import com.tangbuan.wanandroid.databinding.FragmentHomeBinding
import com.tangbuan.wanandroid.databinding.HeaderBannerBinding
import com.tangbuan.wanandroid.extension.getEmptyView
import com.tangbuan.wanandroid.extension.initColors
import com.tangbuan.wanandroid.ui.author.AuthorActivity
import com.tangbuan.wanandroid.utils.LogUtil
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    R.layout.fragment_home,
    HomeViewModel::class.java
) {
    /** 列表总数 */
    private var mTotalCount: Int = 0

    /** 页数 */
    private var mPageNo: Int = 0

    /** 当前列表的数量 */
    private var mCurrentCount: Int = 0

    // 存储网络请求的Banner
    private val mBannerList = ArrayList<Banner>()

    private val mBannerAdapter = MyBannerAdapter(mBannerList)

    private val mAdapter by lazy { ArticleAdapter() }

    companion object {
        fun newInstance() = HomeFragment()
    }

    /*初始化页面*/
    override fun initView() {
        LogUtil.d("初始化开始。。。")


        val headerBannerBinding = DataBindingUtil.inflate<HeaderBannerBinding>(
            layoutInflater,
            R.layout.header_banner,
            null,
            false
        ).apply {
            LogUtil.d("初始化Banner开始。。。")
            banner.apply {
                setAdapter(mBannerAdapter)
                indicator = CircleIndicator(context)
                addBannerLifecycleObserver(viewLifecycleOwner)
            }
        }


        mBinding.apply {
            setVariable(BR.viewModel, mViewModel)
            // TODO:右上角搜索功能
            titleBar.setRightImage(R.drawable.ic_search) {

            }
            /*titleLayout.setRightView(R.drawable.ic_search) {
                SearchActivity.launch(requireContext())
            }*/

            includeList.apply {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter.apply {
                        loadMoreModule.setOnLoadMoreListener { loadMoreData() }
                        LogUtil.d("addHeaderView(headerBannerBinding.root)")
                        addHeaderView(headerBannerBinding.root)
                        addChildClickViewIds(R.id.tv_author, R.id.iv_collect)
                        setOnItemChildClickListener { _, view, position ->
                            when (view.id) {
                                // 查看作者文章列表
                                R.id.tv_author -> {
                                    LogUtil.d("查看作者文章列表 ===> ${mAdapter.getItem(position).userId}")
                                    AuthorActivity.launch(
                                        requireContext(),
                                        mAdapter.getItem(position).userId
                                    )
                                }
                                // 收藏与取消收藏
                                R.id.iv_collect ->
                                    if (mAdapter.getItem(position).collect) {
                                        mViewModel.unCollectArticle(mAdapter.getItem(position).id) {
                                            // 取消收藏成功后,手动更改避免刷新整个列表
                                            mAdapter.getItem(position).collect = false
                                            // 注意:这里position需要+1,因为0位置属于轮播图HeaderView
                                            mAdapter.notifyItemChanged(position + 1)
                                            App.appViewModel.collectEvent.setValue(
                                                CollectData(
                                                    mAdapter.getItem(position).id,
                                                    collect = false
                                                )
                                            )
                                        }
                                    } else {
                                        mViewModel.collectArticle(mAdapter.getItem(position).id) {
                                            // 收藏成功后,手动更改避免刷新整个列表
                                            mAdapter.getItem(position).collect = true
                                            mAdapter.notifyItemChanged(position + 1)
                                            App.appViewModel.collectEvent.setValue(
                                                CollectData(
                                                    mAdapter.getItem(position).id,
                                                    collect = true
                                                )
                                            )
                                        }
                                    }
                            }
                        }
                    }
                }

                swipeRefreshLayout.apply {
                    initColors()
                    setOnRefreshListener { onRefresh() }
                }
            }
        }

        // 直接触发刷新，请求数据
        onRefresh()
    }

    override fun lazyLoadData() {

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            lifecycleScope.launch {
                LogUtil.d("bannerListStateFlow...")
                bannerListStateFlow.flowWithLifecycle(lifecycle).drop(1).collect {
                    LogUtil.d("bannerListStateFlow.flowWithLifecycle(lifecycle)")
                    it.let {
                        mBannerList.apply {
                            clear()
                            addAll(it)
                        }
                    }
                    mBannerAdapter.notifyDataSetChanged()
                }
            }

            articlePageListLiveData.observe(viewLifecycleOwner) {
                it?.let {
                    handleArticleData(it)
                }
            }
        }

        // 全局监听
        App.appViewModel.apply {
            // 收藏监听
            collectEvent.observe(viewLifecycleOwner) {
                for (position in mAdapter.data.indices) {
                    if (mAdapter.data[position].id == it.id) {
                        mAdapter.data[position].collect = it.collect
                        mAdapter.notifyItemChanged(position + 1)
                        break
                    }
                }
            }

            // 用户退出时，收藏应全为false，登录时获取collectIds
            userEvent.observe(this@HomeFragment) {
                if (it != null) {
                    it.collectIds.forEach { id ->
                        for (item in mAdapter.data) {
                            if (id.toInt() == item.id) {
                                item.collect = true
                                break
                            }
                        }
                    }
                } else {
                    for (item in mAdapter.data) {
                        item.collect = false
                    }
                }
                mAdapter.notifyDataSetChanged()
            }
        }

    }

    /**
     * 文章分页数据处理
     *
     * @param pageResponse
     */
    private fun handleArticleData(pageResponse: PageResponse<Article>) {
        mPageNo = pageResponse.curPage
        mTotalCount = pageResponse.pageCount
        val list = pageResponse.datas
        mAdapter.apply {
            if (mPageNo == 1) {
                if (list.isEmpty()) {
                    setEmptyView(recyclerView.getEmptyView())
                }
                // 如果是加载的第一页数据，用 setData()
                setList(list)
            } else {
                // 不是第一页，则用add
                addData(list)
            }
            mCurrentCount = data.size
            loadMoreModule.apply {
                isEnableLoadMore = true
                if (list.size < HomeViewModel.PAGE_SIZE || mCurrentCount == mTotalCount) {
                    // 如果加载到的数据不够一页或都已加载完,显示没有更多数据布局,
                    // 当然后台接口不同分页方式判断方法不同,这个是比较通用的（通常都有TotalCount）
                    loadMoreEnd()
                } else {
                    loadMoreComplete()
                }
            }
            mBinding.includeList.swipeRefreshLayout.isEnabled = true
        }
        mBinding.includeList.swipeRefreshLayout.isRefreshing = false
    }


    /*下拉刷新*/
    private fun onRefresh() {
        mBinding.includeList.swipeRefreshLayout.isRefreshing = true
        // 防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        mViewModel.apply {
            fetchBanners()
            fetchArticlePageList()
        }
    }

    /** 下拉加载更多 */
    private fun loadMoreData() {
        // 上拉加载时禁止下拉刷新
        mBinding.includeList.swipeRefreshLayout.isEnabled = false
        mViewModel.fetchArticlePageList(++mPageNo)
    }


}