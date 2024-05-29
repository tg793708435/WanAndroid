package com.tangbuan.wanandroid.ui.main.wechat

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.base.BaseFragment
import com.tangbuan.wanandroid.data.bean.WeChatClassify
import com.tangbuan.wanandroid.databinding.FragmentWechatBinding

class WechatFragment() :
    BaseFragment<WeChatViewModel, FragmentWechatBinding>(
        R.layout.fragment_wechat,
        WeChatViewModel::class.java
    ) {
    /** TabLayout的标题集合 */
    private val mAuthorTitleList = ArrayList<WeChatClassify>()

    private lateinit var mTabLayoutMediator: TabLayoutMediator

    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = WechatFragment()
    }

    override fun initView() {
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mAuthorTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
                return WechatChildFragment.newInstance(authorId = mAuthorTitleList[position].id)
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

                    text = mAuthorTitleList[position].name
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
            authorTitleListLiveData.observe(viewLifecycleOwner) { list ->
                mBinding.showLoading = false
                mAuthorTitleList.apply {
                    clear()
                    addAll(list)
                }

                mFragmentStateAdapter.notifyDataSetChanged()
                // 这里的方案是直接缓存所有子Fragment然后让子Fragment懒加载数据体验更好
                mBinding.viewPager2.offscreenPageLimit = mAuthorTitleList.size
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
    }
}