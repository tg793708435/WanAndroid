package com.tangbuan.wanandroid.ui.main.wechat

import androidx.lifecycle.MutableLiveData
import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.bean.WeChatClassify
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-27
 */
class WeChatViewModel : BaseViewModel() {
    /** 项目标题列表LiveData */
    val authorTitleListLiveData = MutableLiveData<List<WeChatClassify>>()

    override fun start() {
        // 获取公众号的作者
        fetchAuthorTitleList()
    }

    /** 请求公众号作者标题列表 */
    private fun fetchAuthorTitleList() {
        launch({
            handleRequest(
                Repository.getAuthorTitleList(), {
                    authorTitleListLiveData.value = it.data
                }
            )
        })
    }
}