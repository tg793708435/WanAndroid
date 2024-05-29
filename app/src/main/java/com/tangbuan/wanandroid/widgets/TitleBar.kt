package com.tangbuan.wanandroid.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.databinding.LayoutTitleBinding
import com.tangbuan.wanandroid.extension.hideIme
import com.tangbuan.wanandroid.utils.KeyboardUtils
import org.w3c.dom.Text

/**
 * @brief 自定义TitleBar
 * @author tangbuan
 * @date 2024-05-27
 */
class TitleBar @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(mContext, attrs, defStyleAttr), View.OnClickListener {

    private lateinit var mTitleTv: TextView
    private lateinit var mImgBack: ImageView
    private lateinit var mImgRight: ImageView
    private lateinit var mTxtRight: TextView
    private var titleName: String? = null
    private var backImageVisible: Boolean? = null

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        titleName = attr.getString(R.styleable.TitleBar_titleName)
        backImageVisible = attr.getBoolean(R.styleable.TitleBar_backImageVisible, true)
        attr.recycle()
    }

    /**
     * 初始化布局
     */
    init {
        //加载布局
        View.inflate(mContext, R.layout.layout_title, this)
        val binding = LayoutTitleBinding.inflate(LayoutInflater.from(context), this, true)
        binding.apply {
            //控制头布局，返回关闭页面
            mImgBack = imgBack
            //控制标题
            mTitleTv = txtTitle
            //右边图片
            mImgRight = imgRight
            //右边文字
            mTxtRight = txtRight
        }
        mImgBack.setOnClickListener(this)
        mTitleTv.text = titleName ?: ""
        setBackImageVisible(backImageVisible ?: true)
    }

    /**
     * 设置标题栏标题
     *
     * @param title 标题
     */
    fun setTitle(title: String?) {
        mTitleTv.text = title
    }

    /**
     * 设置返回按钮图片
     *
     * @param imageId 图片id
     */
    fun setBackImage(imageId: Int = 0) {
        if (imageId != 0) {
            mImgBack.setImageResource(imageId)
        }
    }

    /**
     * 设置返回按钮图片是否显示
     *
     * @param imageVisible 是否显示
     */
    fun setBackImageVisible(imageVisible: Boolean) {
        mImgBack.isVisible = imageVisible
    }

    /**
     * 设置右边图片
     *
     * @param imageId 图片id
     * @param onClickListener 点击事件
     */
    fun setRightImage(imageId: Int, onClickListener: OnClickListener) {
        if (imageId != 0) {
            require(mTxtRight.visibility != View.VISIBLE) { "文字和图片不可同时设置" }
            mImgRight.visibility = View.VISIBLE
            mImgRight.setImageResource(imageId)
            setOnClickListener(onClickListener)
        }
    }

    /**
     * 设置右边文字
     *
     * @param text 文字
     */
    fun setRightText(text: String?, onClickListener: OnClickListener) {
        if (text != null) {
            require(mImgRight.visibility != View.VISIBLE) { "文字和图片不可同时设置" }
            mTxtRight.visibility = View.VISIBLE
            mTxtRight.text = text
        }
    }

    /**
     * 设置右边文字背景
     *
     * @param color 颜色
     */
    fun setRightBackColor(color: Int) {
        mTxtRight.setBackgroundColor(color)
    }

    /**
     * 设置右边文字禁止点击
     *
     * @param click 是否可以点击
     */
    fun setRightTextClick(click: Boolean) {
        mTxtRight.isClickable = click
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imgBack) {
            //关闭页面
            (mContext as Activity).hideIme()
            mContext.finish()
        }
    }

    fun setRightTextOnClickListener(onClickListener: OnClickListener) {
        mTxtRight.setOnClickListener(onClickListener)
    }

    fun setRightImgOnClickListener(onClickListener: OnClickListener) {
        mImgRight.setOnClickListener(onClickListener)
    }

    fun setTitleOnClickListener(onClickListener: OnClickListener) {
        mTitleTv.setOnClickListener(onClickListener)
    }

}