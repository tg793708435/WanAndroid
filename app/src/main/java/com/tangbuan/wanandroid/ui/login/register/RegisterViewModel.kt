package com.tangbuan.wanandroid.ui.login.register

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.local.UserManager
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-28
 */
class RegisterViewModel : BaseViewModel() {
    val userName = ObservableField("")
    val userPwd = ObservableField("")
    val userPwdSure = ObservableField("")

    /** 注册按键是否可点击(这样可避免在dataBinding中写较复杂的逻辑) */
    val loginBtnEnable = object : ObservableBoolean(userName, userPwd, userPwdSure) {
        override fun get(): Boolean {
            return !userName.get()?.trim().isNullOrEmpty() && !userPwd.get()
                .isNullOrEmpty() && !userPwdSure.get().isNullOrEmpty()

        }
    }

    override fun start() {}

    /**
     * 注册
     * @param userName 用户名
     * @param pwd 密码
     * @param pwdSure 确认密码
     * @param successCall 成功回调函数
     */
    fun register(userName: String, pwd: String, pwdSure: String, successCall: () -> Any? = {}) {
        launch({
            handleRequest(
                Repository.register(userName, pwd, pwdSure),
                successBlock = {
                    UserManager.saveLastUserName(userName)
                    successCall.invoke()
                }
            )
        })
    }
}