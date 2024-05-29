package com.tangbuan.wanandroid.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-25
 */
data class PageResponse<T>(
    var curPage: Int = 0,
    var datas: List<T>,
    var offset: Int = 0,
    var over: Boolean = false,
    var pageCount: Int = 0,
    var size: Int = 0,
    var total: Int = 0
)
