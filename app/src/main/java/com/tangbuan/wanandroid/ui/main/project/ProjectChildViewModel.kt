package com.tangbuan.wanandroid.ui.main.project

import androidx.lifecycle.MutableLiveData
import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.bean.Article
import com.tangbuan.wanandroid.data.bean.PageResponse
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch

class ProjectChildViewModel : BaseViewModel() {

    /** 项目分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>?>()

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    override fun start() {}

    /** 请求最新项目分页列表 */
    fun fetchNewProjectPageList(pageNo: Int = 0) {
        launch({
            handleRequest(
                Repository.getNewProjectPageList(pageNo, PAGE_SIZE),
                { articlePageListLiveData.value = it.data })
        })
    }

    /** 请求项目分页列表 */
    fun fetchProjectPageList(pageNo: Int = 1, categoryId: Int) {
        launch({
            handleRequest(
                Repository.getProjectPageList(pageNo, PAGE_SIZE, categoryId),
                { articlePageListLiveData.value = it.data })
        })
    }

    /**
     * 收藏文章
     * @param id 文章id
     */
    /*fun collectArticle(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(Repository.collectArticle(id), {
                successCallBack.invoke()
            })
        })
    }*/

    /**
     * 取消收藏文章
     * @param id 文章id
     */
   /* fun unCollectArticle(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(Repository.unCollectArticle(id), {
                successCallBack.invoke()
            })
        })
    }*/
}