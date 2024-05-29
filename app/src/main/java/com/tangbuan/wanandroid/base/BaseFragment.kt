package com.tangbuan.wanandroid.base

import androidx.databinding.ViewDataBinding
import com.tangbuan.wanandroid.ui.login.LoginActivity

/**
 *
 * 继承自BaseVMBFragment
 * 在这里只做了统一处理跳转登录页面的逻辑
 *
 * @brief description
 * @author tangbuan
 * @date 2024-05-25
 */
abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding>(contentViewResId: Int, VMClass:Class<VM>) :
    BaseVMBFragment<VM, B>(contentViewResId, VMClass) {

    override fun createObserve() {
        super.createObserve()
        mViewModel.errorResponse.observe(viewLifecycleOwner) {
            if (it?.errorCode == -1001) { // 需要登录
                LoginActivity.launch(requireContext())
            }
        }
    }
}