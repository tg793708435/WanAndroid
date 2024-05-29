package com.tangbuan.wanandroid.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * DataBinding的自定义属性
 * @author LTP  2022/4/2
 */
@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) {
    load(url)
}