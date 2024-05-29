package com.tangbuan.wanandroid.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.data.local.UserManager
import com.tangbuan.wanandroid.ui.login.LoginActivity
import com.tangbuan.wanandroid.utils.LogUtil

/**
 * ImageView利用Glide加载图片
 * @param url 图片url（可远程可本地）
 * @param showPlaceholder 是否展示placeholder，默认为true
 */
fun ImageView.load(url: String, showPlaceholder: Boolean = true) {
//    LogUtil.d("ImageView利用Glide加载图片 ====> $url")
    if (showPlaceholder) {
        Glide.with(context).load(url)
            .placeholder(R.drawable.ic_default_img)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    } else {
        Glide.with(context).load(url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }
}

/**
 * 初始化普通的toolbar 只设置标题
 *
 * @param titleStr 标题
 */
fun Toolbar.initTitle(titleStr: String = "") {
    title = titleStr
}

/**
 * 初始化返回键
 *
 * @param backImg 返回键资源路径
 * @param onBack 返回事件
 */
fun Toolbar.initClose(
    backImg: Int = R.drawable.ic_back,
    onBack: () -> Unit
) {
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack() }
}



/**
 * RecyclerView列表为空时的显示视图
 */
fun RecyclerView.getEmptyView(message: String = "列表为空"): View {
    return LayoutInflater.from(context)
        .inflate(R.layout.layout_empty, parent as ViewGroup, false).apply {
            findViewById<TextView>(R.id.tv_empty).text = message
        }
}

/**
 * SwipeRefreshLayout设置加载主题颜色
 * @author LTP  2022/3/24
 */
fun SwipeRefreshLayout.initColors() {
    setColorSchemeResources(
        R.color.purple_500, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light
    )
}


/**
 * 隐藏ime
 */
fun Activity?.hideIme(currentFocusView: View? = null) {
    if (this == null || window == null) return
    val view = currentFocusView ?: window.decorView
    view.clearFocus()
    val controller = WindowCompat.getInsetsController(window, view)
    controller.hide(ime())
}

/**
 * 显示ime
 */
fun Activity?.showIme(currentFocusView: View? = null) {
    if (this == null || window == null) return
    val view = currentFocusView ?: window.decorView
    view.isFocusable = true
    view.requestFocus()
    view.findFocus()
    val controller = WindowCompat.getInsetsController(window, view)
    Handler(Looper.getMainLooper()).postDelayed({
        controller.show(ime())
    }, 100L)
}

/**
 * 需要验证登录状态的操作
 *
 * @param action 函数参数，已登录状态时的处理
 */
fun Context.launchCheckLogin(action: (context: Context) -> Unit) {
    if (UserManager.isLogin()) {
        action.invoke(this)
    } else {
        LoginActivity.launch(this)
    }
}


/** 加载框 */
@SuppressLint("StaticFieldLeak")
private var mLoadingDialog: MaterialDialog? = null

/** 打开加载框 */
fun AppCompatActivity.showLoading(message: String = "请求网络中") {
    if (!this.isFinishing) {
        if (mLoadingDialog == null) {
            mLoadingDialog = MaterialDialog(this)
                .cancelable(true)
                .cancelOnTouchOutside(false)
                .cornerRadius(6f)
                .customView(R.layout.dialog_loading)
                .maxWidth(literal = 120f.px.toInt())
                .lifecycleOwner(this)
                .apply {
                    getCustomView().findViewById<TextView>(R.id.tv_loadingMsg).text = message
                }

        }
        mLoadingDialog?.show()
    }
}

/** 隐藏Loading加载框 */
fun hideLoading() {
    mLoadingDialog?.dismiss()
    mLoadingDialog = null
}