package com.tangbuan.wanandroid.data.bean


/**
 * 其他文章作者信息实体
 */
data class OtherAuthor(
    val coinInfo: CoinInfo,
    val shareArticles: PageResponse<Article>
)