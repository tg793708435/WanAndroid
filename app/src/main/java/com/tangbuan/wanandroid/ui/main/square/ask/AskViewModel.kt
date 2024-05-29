package com.tangbuan.wanandroid.ui.main.square.ask

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
class AskViewModel :BaseViewModel(){
    /** 每日一问分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>?>()

    override fun start() {}

    /** 请求每日一问分页列表 */
    fun fetchAskPageList(pageNo: Int = 1) {
        launch({
            handleRequest(
                Repository.getAskPageList(pageNo),
                { articlePageListLiveData.value = it.data })
        })
    }
}