package com.tangbuan.wanandroid.ui.main.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.data.bean.Article
import com.tangbuan.wanandroid.databinding.ListItemArticleBinding
import com.tangbuan.wanandroid.ui.web.WebActivity

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-25
 */
class ArticleAdapter : BaseQuickAdapter<Article, BaseDataBindingHolder<ListItemArticleBinding>>(R.layout.list_item_article), LoadMoreModule{
    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemArticleBinding>, item: Article) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()  // 立即绑定
            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }
    }

}