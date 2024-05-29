package com.tangbuan.wanandroid.ui.main.square

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.base.BaseFragment
import com.tangbuan.wanandroid.databinding.FragmentViewpagerBinding
import com.tangbuan.wanandroid.extension.px
import com.tangbuan.wanandroid.ui.main.square.ask.AskFragment
import com.tangbuan.wanandroid.ui.main.square.square.SquareChildFragment

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-28
 */
class SquareFragment : BaseFragment<SquareViewModel, FragmentViewpagerBinding>(
    R.layout.fragment_viewpager,
    SquareViewModel::class.java
) {

    /** 标题 */
    private val mTitleList = arrayListOf("广场", "每日一问"/*, "体系", "导航"*/)
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    private lateinit var mTabLayoutMediator: TabLayoutMediator
    private lateinit var mFragmentStateAdapter: FragmentStateAdapter


    private val mPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 0) {
                mBinding.titleLayout.setRightImage(R.drawable.ic_add) {
//                    requireContext().launchCheckLogin { AddArticleActivity.launch(it) }
                }
            } else {
//                mBinding.titleLayout.setRightView("")
            }
        }
    }

    init {
        mFragmentList.add(SquareChildFragment.newInstance())
        mFragmentList.add(AskFragment.newInstance())
        /* mFragmentList.add(SystemFragment.newInstance())
         mFragmentList.add(NavigationFragment.newInstance())*/
    }

    companion object {
        fun newInstance() = SquareFragment()
    }

    override fun initView() {
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mFragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return mFragmentList[position]
            }
        }

        mBinding.apply {
            tabLayout.layoutParams = ConstraintLayout.LayoutParams(tabLayout.layoutParams).apply {
                marginStart = 40f.px.toInt()
                marginEnd = 60f.px.toInt()
                topToTop = mBinding.root.top
                topMargin = 6f.px.toInt()
            }

            viewPager2.apply {
                adapter = mFragmentStateAdapter
                offscreenPageLimit = mFragmentList.size
                registerOnPageChangeCallback(mPageChangeCallback)
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

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
        mBinding.viewPager2.unregisterOnPageChangeCallback(mPageChangeCallback)
    }

    override fun lazyLoadData() {

    }
}