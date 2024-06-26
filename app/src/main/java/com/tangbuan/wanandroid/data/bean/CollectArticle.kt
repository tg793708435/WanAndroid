package com.tangbuan.wanandroid.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @brief 收藏文章
 * @author tangbuan
 * @date 2024-05-29
 */
@Parcelize
data class CollectArticle(
    var author: String = "",
    var chapterId: Int = 0,
    var chapterName: String = "",
    var courseId: Int = 0,
    var desc: String = "",
    var envelopePic: String = "",
    var id: Int = 0,
    var link: String = "",
    var niceDate: String = "",
    var origin: String = "",
    var originId: Int = 0,
    var publishTime: Long = 0,
    var title: String = "",
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0
) :Parcelable