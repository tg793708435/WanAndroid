package com.tangbuan.wanandroid.data.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.tangbuan.wanandroid.base.BaseApp.Companion.appContext
import com.tangbuan.wanandroid.utils.LogUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @brief Retrofit管理类
 * @author tangbuan
 * @date 2024-05-25
 */
object RetrofitManager {
    /*请求超时时间*/
    private const val TIME_OUT_SECONDS = 10

    /*请求Cookie*/
    /** 请求cookie */
    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(appContext)
        )
    }

    private const val BASE_URL = "https://www.wanandroid.com"

    /** OkHttpClient相关配置 */
    private val client: OkHttpClient
        get() = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            //设置缓存配置,缓存最大10M,设置了缓存之后可缓存请求的数据到data/data/包名/cache/net_cache目录中
            .cache(Cache(File(appContext.cacheDir, "net_cache"), 10 * 1024 * 1024))
            .cookieJar(cookieJar)
            .build()

    fun <T> getService(serviceClass: Class<T>, baseUrl: String? = null): T {
        LogUtil.d(BASE_URL)
        return Retrofit.Builder()
            .baseUrl(baseUrl ?: BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }
}