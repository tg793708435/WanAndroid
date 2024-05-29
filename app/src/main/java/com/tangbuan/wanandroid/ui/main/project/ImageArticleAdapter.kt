package com.tangbuan.wanandroid.ui.main.project

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.data.bean.Article
import com.tangbuan.wanandroid.databinding.ListItemArticleImageBinding
import com.tangbuan.wanandroid.ui.web.WebActivity

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-27
 */
class ImageArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ListItemArticleImageBinding>>(
        R.layout.list_item_article_image
    ), LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<ListItemArticleImageBinding>,
        item: Article
    ) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()

            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }

    }
}