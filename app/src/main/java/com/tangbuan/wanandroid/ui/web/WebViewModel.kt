package com.tangbuan.wanandroid.ui.web

import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.bean.CollectUrl
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-29
 */
class WebViewModel : BaseViewModel() {
    override fun start() {}


    /**
     * 收藏网址
     * @param name 网址名
     * @param link 网址链接
     */
    fun collectUrl(
        title: String,
        url: String,
        successCallBack: (CollectUrl: CollectUrl?) -> Any? = {}
    ) {
        launch(
            {
                handleRequest(
                    Repository.collectUrl(title, url), {
                        successCallBack(it.data)
                    }
                )
            }
        )
    }

    /** 取消收藏网址*/
    fun unCollectUrl(id: Int, successCallBack: () -> Any? = {}) {
        launch(
            {
                handleRequest(Repository.unCollectUrl(id), {
                    successCallBack.invoke()
                })
            }
        )

    }

    /**
     * 收藏文章
     * @param id 文章id
     */
    fun collectArticle(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(Repository.collectArticle(id), {
                successCallBack.invoke()
            })
        })
    }

    fun unCollectArticle(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(Repository.unCollectArticle(id), {
                successCallBack.invoke()
            })
        })
    }


}