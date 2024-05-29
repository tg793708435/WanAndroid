package com.tangbuan.wanandroid.ui.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.tangbuan.wanandroid.base.App
import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.local.UserManager
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch
import com.tangbuan.wanandroid.utils.LogUtil

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-28
 */
class LoginViewModel : BaseViewModel() {
    val userName = ObservableField("")
    val userPwd = ObservableField("")

    /** 登录按键是否可点击(这样可避免在dataBinding中写较复杂的逻辑) */
    val loginBtnEnable = object : ObservableBoolean(userName, userPwd) {
        override fun get(): Boolean {
            val res = !userName.get()?.trim().isNullOrEmpty() && !userPwd.get().isNullOrEmpty()
            LogUtil.d("loginBtnEnable ====>  $res")
            return res
        }
    }

    override fun start() {
        userName.set(UserManager.getLastUserName())
    }

    /**
     * 登录
     * @param userName 用户名
     * @param pwd 密码
     */
    fun login(userName: String, pwd: String, successCall: () -> Any? = {}) {
        launch({
            handleRequest(Repository.login(userName, pwd), successBlock = {
                UserManager.saveLastUserName(userName)
                UserManager.saveUser(it.data)
                App.appViewModel.userEvent.value = it.data
                successCall.invoke()
            })
        })
    }
}