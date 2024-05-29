package com.tangbuan.wanandroid.ui.main.mine

import com.tangbuan.wanandroid.BR
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.base.App
import com.tangbuan.wanandroid.base.BaseFragment
import com.tangbuan.wanandroid.data.local.UserManager
import com.tangbuan.wanandroid.databinding.FragmentMineBinding
import com.tangbuan.wanandroid.extension.initColors
import com.tangbuan.wanandroid.extension.launchCheckLogin
import com.tangbuan.wanandroid.ui.login.LoginActivity
import com.tangbuan.wanandroid.utils.LogUtil


class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>(
    R.layout.fragment_mine,
    MineViewModel::class.java
) {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView() {
        mBinding.apply {
            setVariable(BR.viewModel, mViewModel)
            swipeRefreshLayout.apply {
                initColors()
                setOnRefreshListener { onRefresh() }
            }

            clUser.setOnClickListener {
                if (!UserManager.isLogin()) {
                    LogUtil.d("MineFragment ===> 用户未登录")
                    LoginActivity.launch(requireContext())
                }
            }

            // 我的积分
            tvPoints.setOnClickListener {
//                IntegralRankActivity.launch(requireContext())
            }

            // 我的收藏（需要登录）
            tvCollect.setOnClickListener {
//                requireContext().launchCheckLogin { CollectActivity.launch(it) }
            }
            // 我分享的文章（需要登录）
            tvArticle.setOnClickListener {
//                requireContext().launchCheckLogin { MyArticleActivity.launch(it) }
            }

            // 开源网站
            tvWeb.setOnClickListener {
                /* WebActivity.launch(
                     requireContext(),
                     Banner(title = "玩Android网站", url = "https://www.wanandroid.com/")
                 )*/
            }

            // 设置
            tvSettings.setOnClickListener {
//                SettingActivity.launch(requireContext())
            }
        }
        onRefresh()
    }

    /** 下拉刷新 */
    private fun onRefresh() {
        if (UserManager.isLogin()) {
            mBinding.swipeRefreshLayout.isEnabled = true
            mBinding.swipeRefreshLayout.isRefreshing = true
            mViewModel.fetchPoints()
        } else {
            mBinding.swipeRefreshLayout.isEnabled = false
        }
    }

    override fun createObserve() {
        super.createObserve()
         App.appViewModel.userEvent.observe(this) {
             mViewModel.user.set(it)
             if (it == null) {
                 mViewModel.integral.value = null
             }
             onRefresh()
         }

        mViewModel.integral.observe(this) {
            mBinding.apply {
                swipeRefreshLayout.isRefreshing = false
                tvInfo.text = "id：${it?.userId ?: '—'}　排名：${it?.rank ?: '—'}"
                tvPointsNum.text = "${it?.coinCount ?: '—'}"
            }
        }
    }

    override fun lazyLoadData() {
    }

    override fun requestError(msg: String?) {
        super.requestError(msg)
        mBinding.swipeRefreshLayout.isRefreshing = false
    }
}