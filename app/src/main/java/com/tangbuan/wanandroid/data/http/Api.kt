package com.tangbuan.wanandroid.data.http

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
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @brief Http接口，Retrofit的请求Service
 * @author tangbuan
 * @date 2024-05-25
 */
interface Api {


    /** 登录 */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ): ApiResponse<User>



    /** 获取首页banner数据 */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<Banner>>

    /*获取首页文章*/
    @GET("article/list/{pageNo}/json")
    suspend fun getArticlePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int,
        @Query("cid") cid: Int? = null
    ): ApiResponse<PageResponse<Article>>

    @GET("article/top/json")
    suspend fun getArticleTopList(): ApiResponse<List<Article>>

    /** 获取项目分类数据 */
    @GET("project/tree/json")
    suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>>

    /** 获取项目列表分页数据 */
    @GET("project/list/{pageNo}/json")
    suspend fun getProjectPageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int,
        @Query("cid") categoryId: Int
    ): ApiResponse<PageResponse<Article>>

    /** 获取最新项目列表分页数据 */
    @GET("project/list/{pageNo}/json")
    suspend fun getNewProjectPageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>

    /** 获取公众号作者列表*/
    @GET("wxarticle/chapters/json")
    suspend fun getAuthorTitleList(): ApiResponse<List<WeChatClassify>>

    /** 获取公众号作者文章分页列表*/
    @GET("wxarticle/list/{authorId}/{pageNo}/json")
    suspend fun getAuthorArticlePageList(
        @Path("authorId") authorId: Int,
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>

    /** 获取每日一问列表分页数据 */
    @GET("wenda/list/{pageNo}/json")
    suspend fun getAskPageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<Article>>

    /** 获取广场列表分页数据 */
    @GET("user_article/list/{pageNo}/json")
    suspend fun getSquarePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int): ApiResponse<PageResponse<Article>?>

    /** 获取个人积分 */
    @GET("lg/coin/userinfo/json")
    suspend fun getUserIntegral(): ApiResponse<CoinInfo>

    /** 注册 */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") pwd: String,
        @Field("repassword") pwdSure: String
    ): ApiResponse<Any?>

    /** 取消收藏站内文章 */
    @POST("lg/collect/deletetool/json")
    suspend fun unCollectUrl(@Query("id") id: Int): ApiResponse<Any?>

    /** 收藏网址 */
    @POST("lg/collect/addtool/json")
    suspend fun collectUrl(
        @Query("name") name: String,
        @Query("link") link: String
    ): ApiResponse<CollectUrl?>

    /** 收藏站内文章 */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 取消收藏站内文章 */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 获取其他作者分享的文章分页列表 */
    @GET("user/{id}/share_articles/{page}/json")
    suspend fun getOtherAuthorArticlePageList(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): ApiResponse<OtherAuthor>

}