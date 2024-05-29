package com.tangbuan.wanandroid.base

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-28
 */
class App:BaseApp() {
    companion object {
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]
    }
}