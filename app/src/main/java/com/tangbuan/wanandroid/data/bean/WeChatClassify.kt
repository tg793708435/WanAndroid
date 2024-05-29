package com.tangbuan.wanandroid.data.bean

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-27
 */
data class WeChatClassify(
    var author: String = "",
    var courseId: Int = 0,
    var cover: String = "",
    var desc: String = "",
    var id: Int = 0,
    var lisense: String = "",
    var lisenseLink: String = "",
    var name: String = "",
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var type: Int = 0,
    var userControlSetTop: Boolean = false,
    var visible: Int = 0
)