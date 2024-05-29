package com.tangbuan.wanandroid.ui.main.mine

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.bean.CoinInfo
import com.tangbuan.wanandroid.data.bean.User
import com.tangbuan.wanandroid.data.local.UserManager
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-28
 */
class MineViewModel : BaseViewModel() {
    val user = ObservableField<User?>()
    val integral = MutableLiveData<CoinInfo?>()

    // 初始化用户名称
    val userName = object : ObservableField<String>(user) {
        override fun get(): String {
            return if (UserManager.isLogin()) user.get()!!.nickname else "请登录"
        }
    }

    override fun start() {
        if (UserManager.isLogin()) {
            user.set(UserManager.getUser())
        }
    }

    /** 获取个人积分 */
    fun fetchPoints() {
        launch({
            val response = Repository.getUserIntegral()
            handleRequest(response, {
                integral.value = response.data
            })
        })
    }
}