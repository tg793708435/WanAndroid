package com.tangbuan.wanandroid.data.local

import com.tangbuan.wanandroid.data.bean.User
import com.tencent.mmkv.MMKV

/**
 * @brief 数据管理类
 * @author tangbuan
 * @date 2024-05-28
 */
object UserManager {

    /** MMKV独有的mmapId */
    private const val MMKV_MAP_ID = "user"

    /** 保存登录成功的用户的Json串的KEY */
    private const val KEY_USER = "user_data"

    /** 保存最后一次登录成功的用户名的KEY */
    private const val KEY_LAST_USER_NAME = "lastUserName"

    private val mmkv by lazy { MMKV.mmkvWithID(MMKV_MAP_ID) }
    /**
     * 是否已登录(自动登录的判断)
     *
     * @return 是否已登录
     */
    fun isLogin(): Boolean {
        return getUser() != null
    }


    /**
     * 获取存储的本地用户信息
     *
     * @return 本地用户信息
     */
    fun getUser(): User? {
        return mmkv.decodeParcelable(KEY_USER, User::class.java)
    }

    /**
     * 获取上一次登录成功的用户名(退出时显示在登录名里)
     *
     * @return 上一次登录成功的用户名
     */
    fun getLastUserName(): String {
        return mmkv.decodeString(KEY_LAST_USER_NAME, "")!!
    }

    /**
     * 存储上一次登录成功的用户名(退出时显示在登录名里)
     *
     * @param userName 用户名
     */
    fun saveLastUserName(userName: String) {
        mmkv.encode(KEY_LAST_USER_NAME, userName)
    }

    /**
     * 存储用户到本地
     *
     * @param user    用户
     */
    fun saveUser(user: User) {
        mmkv.encode(KEY_USER, user)
    }
}