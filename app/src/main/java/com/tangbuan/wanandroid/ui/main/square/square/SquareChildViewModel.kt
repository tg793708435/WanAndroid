package com.tangbuan.wanandroid.ui.main.square.square

import androidx.lifecycle.MutableLiveData
import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.bean.Article
import com.tangbuan.wanandroid.data.bean.PageResponse
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-28
 */
class SquareChildViewModel :BaseViewModel(){
    /** 广场分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>>()


    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    /** 请求广场分页列表 */
    fun fetchSquarePageList(pageNo: Int = 0) {
        launch({
            handleRequest(
                Repository.getSquarePageList(pageNo, PAGE_SIZE),
                { articlePageListLiveData.value = it.data })
        })
    }

    override fun start() {}
}