package com.tangbuan.wanandroid.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-25
 */
@Parcelize
data class Tag(
    var name: String = "",
    var url: String = ""
) : Parcelable