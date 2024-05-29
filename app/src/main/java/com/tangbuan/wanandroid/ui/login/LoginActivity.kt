package com.tangbuan.wanandroid.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.tangbuan.wanandroid.BR
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.base.BaseActivity
import com.tangbuan.wanandroid.databinding.ActivityLoginBinding
import com.tangbuan.wanandroid.extension.hideLoading
import com.tangbuan.wanandroid.extension.showLoading
import com.tangbuan.wanandroid.ui.login.register.RegisterActivity

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(
    R.layout.activity_login,
    LoginViewModel::class.java
) {
    companion object {

        /**
         * 页面启动
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        // Activity Results需要先注册
        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    mViewModel.userName.set(it.data?.getStringExtra(RegisterActivity.EXTRA_RESULT_USER_NAME))
                }
            }

        mBinding.apply {
            setVariable(BR.viewModel, mViewModel)
            tvRegister.setOnClickListener {
                // 使用Activity Results API
                registerForActivityResult.launch(RegisterActivity.newIntent(this@LoginActivity))
            }

            btnLogin.setOnClickListener {
                showLoading("登陆中...")
                mViewModel.login(mViewModel.userName.get()!!, mViewModel.userPwd.get()!!) {
                    hideLoading()
                    onBackPressed()
                }
            }

            ivLogo.setOnClickListener {
//                launchChangeIpActivity()
            }
        }
    }


}