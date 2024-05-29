package com.tangbuan.wanandroid.ui.main.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.base.BaseFragment
import com.tangbuan.wanandroid.data.bean.ProjectTitle
import com.tangbuan.wanandroid.databinding.FragmentProjectBinding
import com.tangbuan.wanandroid.utils.LogUtil

class ProjectFragment : BaseFragment<ProjectViewModel, FragmentProjectBinding>(
    R.layout.fragment_project,
    ProjectViewModel::class.java
) {
    /** TabLayout的标题集合 */
    private val mProjectTitleList = ArrayList<ProjectTitle>()
    private val mTitleList = ArrayList<String>()

    private lateinit var mTabLayoutMediator: TabLayoutMediator
    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initView() {
        LogUtil.d("ProjectFragment initView")
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
                LogUtil.d("ProjectFragment启动子fragment")
                return if (position == 0) ProjectChildFragment.newInstance(true)
                else ProjectChildFragment.newInstance(categoryId = mProjectTitleList[position - 1].id)
            }
        }

        mBinding.apply {
            // 由于标题也需要请求（只有请求完标题后才会加载Fragment从而显示swipeRefreshLayout），
            // 所以在请求标题之前也需要一个loading
            showLoading = true

            viewPager2.apply {
                adapter = mFragmentStateAdapter
            }

            mTabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.apply {
                    // 处理长按出现toast的问题
                    view.setOnLongClickListener { true }
                    text = mTitleList[position]
                }
            }.apply { attach() }
        }
    }

    override fun lazyLoadData() {

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            LogUtil.d("ProjectFragment viewModel")
            projectTitleListLiveData.observe(viewLifecycleOwner) { list ->
                mBinding.showLoading = false
                mProjectTitleList.apply {
                    clear()
                    list?.let { addAll(it) }
                }

                mTitleList.apply {
                    clear()
                    mTitleList.add("最新项目")
                }
                list?.forEach {
                    LogUtil.d(it.name)
                    mTitleList.add(it.name.toString())
                }
                LogUtil.d("mTitleList ====> ${mTitleList.toString()}")
                mFragmentStateAdapter.notifyDataSetChanged()
                // 这里的方案是直接缓存所有子Fragment然后让子Fragment懒加载数据体验更好
                mBinding.viewPager2.offscreenPageLimit = mTitleList.size
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
    }
}