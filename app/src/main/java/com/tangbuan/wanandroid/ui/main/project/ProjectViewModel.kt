package com.tangbuan.wanandroid.ui.main.project

import androidx.lifecycle.MutableLiveData
import com.tangbuan.wanandroid.base.BaseViewModel
import com.tangbuan.wanandroid.data.Repository
import com.tangbuan.wanandroid.data.bean.ProjectTitle
import com.tangbuan.wanandroid.extension.handleRequest
import com.tangbuan.wanandroid.extension.launch
import com.tangbuan.wanandroid.utils.LogUtil

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-27
 */
class ProjectViewModel : BaseViewModel() {
    /** 项目标题列表LiveData */
    val projectTitleListLiveData = MutableLiveData<List<ProjectTitle>>()

    override fun start() {
        // viewModel加载完成就去请求项目标题列表
        fetchProjectTitleList()
    }

    private fun fetchProjectTitleList() {
        LogUtil.d("fetchProjectTitleList  获取子标题")
        launch({
            handleRequest(
                Repository.getProjectTitleList(), {
                    projectTitleListLiveData.value = it.data
                }
            )
        })
    }
}