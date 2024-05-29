package com.tangbuan.wanandroid.ui.main.home

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tangbuan.wanandroid.data.bean.Banner
import com.tangbuan.wanandroid.extension.load
import com.tangbuan.wanandroid.utils.LogUtil
import com.youth.banner.adapter.BannerAdapter

/**
 * 图片轮播Banner的Adapter
 *
 * @author LTP 2022/3/24
 */
class MyBannerAdapter(dataList: ArrayList<Banner>) :
    BannerAdapter<Banner, MyBannerAdapter.BannerViewHolder>(dataList) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
//        LogUtil.d("onCreateHolder")
        val imageView = ImageView(parent?.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: Banner, position: Int, size: Int) {
//        LogUtil.d("onBindView")
        holder.imageView.apply {
            load(data.imagePath)
//            setOnClickListener { WebActivity.launch(context, data) }
        }
    }

   inner class BannerViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(imageView)
}
