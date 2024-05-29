package com.tangbuan.wanandroid.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-28
 */
@Parcelize
data class User(
    var admin: Boolean = false,
    var chapterTops: List<String> = listOf(),
    var coinCount: Int = 0,
    var collectIds: List<String> = listOf(),
    var email: String = "",
    var icon: String = "",
    var id: Int = 0,
    var nickname: String = "",
    var password: String = "",
    var publicName: String = "",
    var token: String = "",
    var type: Int = 0,
    var username: String = ""
):Parcelable