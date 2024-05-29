package com.tangbuan.wanandroid.data

import com.tangbuan.wanandroid.data.bean.ApiResponse
import com.tangbuan.wanandroid.data.bean.Article
import com.tangbuan.wanandroid.data.bean.Banner
import com.tangbuan.wanandroid.data.bean.CoinInfo
import com.tangbuan.wanandroid.data.bean.CollectUrl
import com.tangbuan.wanandroid.data.bean.OtherAuthor
import com.tangbuan.wanandroid.data.bean.PageResponse
import com.tangbuan.wanandroid.data.bean.ProjectTitle
import com.tangbuan.wanandroid.data.bean.User
import com.tangbuan.wanandroid.data.bean.WeChatClassify
import com.tangbuan.wanandroid.data.http.Api
import com.tangbuan.wanandroid.data.http.RetrofitManager

/**
 * @brief 数据仓库
 * @author tangbuan
 * @date 2024-05-25
 */
object Repository : Api {

    private val service by lazy {
        RetrofitManager.getService(Api::class.java)
    }

    override suspend fun login(username: String, pwd: String): ApiResponse<User> {
        return service.login(username, pwd)
    }

    override suspend fun collectUrl(name: String, link: String): ApiResponse<CollectUrl?> {
        return service.collectUrl(name, link)
    }

    override suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        return service.collectArticle(id)
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        return service.unCollectArticle(id)
    }

    override suspend fun getOtherAuthorArticlePageList(
        id: Int,
        page: Int
    ): ApiResponse<OtherAuthor> {
        return service.getOtherAuthorArticlePageList(id, page)
    }

    override suspend fun getBanner(): ApiResponse<List<Banner>> {
        return service.getBanner()
    }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int,
        cid: Int?
    ): ApiResponse<PageResponse<Article>> {
        return service.getArticlePageList(pageNo, pageSize, cid)
    }

    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        return service.getArticleTopList()
    }

    override suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>> {
        return service.getProjectTitleList()
    }

    override suspend fun getProjectPageList(
        pageNo: Int,
        pageSize: Int,
        categoryId: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getProjectPageList(pageNo, pageSize, categoryId)
    }

    override suspend fun getNewProjectPageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getNewProjectPageList(pageNo, pageSize)
    }

    override suspend fun getAuthorTitleList(): ApiResponse<List<WeChatClassify>> {
        return service.getAuthorTitleList()
    }

    override suspend fun getAuthorArticlePageList(
        authorId: Int,
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getAuthorArticlePageList(authorId, pageNo, pageSize)
    }

    override suspend fun getAskPageList(pageNo: Int): ApiResponse<PageResponse<Article>> {
        return service.getAskPageList(pageNo)
    }

    override suspend fun getSquarePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>?> {
        return service.getSquarePageList(pageNo, pageSize)
    }

    override suspend fun getUserIntegral(): ApiResponse<CoinInfo> {
        return service.getUserIntegral()
    }

    override suspend fun register(
        username: String,
        pwd: String,
        pwdSure: String
    ): ApiResponse<Any?> {
        return service.register(username, pwd, pwdSure)
    }

    override suspend fun unCollectUrl(id: Int): ApiResponse<Any?> {
        return service.unCollectUrl(id)
    }
}