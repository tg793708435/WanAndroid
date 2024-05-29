package com.tangbuan.wanandroid.ui.login.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tangbuan.wanandroid.BR
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.base.BaseActivity
import com.tangbuan.wanandroid.databinding.ActivityRegisterBinding
import com.tangbuan.wanandroid.extension.hideLoading
import com.tangbuan.wanandroid.extension.showLoading
import com.tangbuan.wanandroid.utils.ToastUtil

class RegisterActivity :
    BaseActivity<RegisterViewModel, ActivityRegisterBinding>(
        R.layout.activity_register,
        RegisterViewModel::class.java
    ) {


    companion object {
        /** 返回给LoginActivity的extra产量key */
        const val EXTRA_RESULT_USER_NAME = "user_name"

        /**
         * 传递启动的Intent
         * @param context Context
         */
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            setVariable(BR.viewModel,mViewModel)
            btnRegister.setOnClickListener {
                when {
                    mViewModel.userPwd.get()!!.length < 6 ->
                        ToastUtil.showLong(this@RegisterActivity, "密码最少6位")

                    mViewModel.userPwd.get() != mViewModel.userPwdSure.get() ->
                        ToastUtil.showLong(this@RegisterActivity, "密码不一致")

                    else -> {
                        showLoading("注册中...")
                        mViewModel.register(
                            mViewModel.userName.get()!!,
                            mViewModel.userPwd.get()!!,
                            mViewModel.userPwdSure.get()!!,
                        ) {
                            hideLoading()
                            /**
                             loginActivity通过StartActivityForResult启动的registerActivity
                             需要返回result
                             */
                            setResult(
                                RESULT_OK,
                                Intent().apply {
                                    putExtra(
                                        EXTRA_RESULT_USER_NAME,
                                        mViewModel.userName.get()
                                    )
                                })
                            finish()
                        }
                    }
                }
            }
        }
    }
}