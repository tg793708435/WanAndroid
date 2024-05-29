package com.tangbuan.wanandroid.base

import androidx.lifecycle.MutableLiveData
import com.tangbuan.wanandroid.data.bean.CollectData
import com.tangbuan.wanandroid.data.bean.User

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-25
 */
class AppViewModel :BaseViewModel(){
    override fun start() {}

    /** 全局用户 */
    val userEvent = MutableLiveData<User?>()

    /** 分享添加文章 */
    val shareArticleEvent = MutableLiveData<Boolean>()

    /** 文章收藏 */
    val collectEvent = MutableLiveData<CollectData>()
}