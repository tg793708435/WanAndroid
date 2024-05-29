package com.tangbuan.wanandroid.ui.main.wechat

import androidx.lifecycle.MutableLiveData
import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.bean.Article
import com.tangbuan.wanandroid.data.bean.PageResponse
import com.tangbuan.wanandroid.data.bean.WeChatClassify
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch
import com.tangbuan.wanandroid.utils.LogUtil

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-27
 */
class WechatChildViewModel : BaseViewModel() {

    /** 项目分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>>()

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    override fun start() {}

    fun fetchAuthorArticlePageList(authorId: Int, pageNo: Int) {
        launch({
            handleRequest(
                Repository.getAuthorArticlePageList(authorId, pageNo, PAGE_SIZE), {
                    LogUtil.d("AuthorArticlePageList ====> ${it.data.toString()}")
                    articlePageListLiveData.value = it.data
                }
            )
        })
    }


}