package com.tangbuan.wanandroid.extension

import android.content.res.Resources
import android.util.TypedValue

// 扩展Float，将float转为px
val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )