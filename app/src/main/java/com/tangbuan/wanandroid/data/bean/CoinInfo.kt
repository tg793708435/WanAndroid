package com.tangbuan.wanandroid.data.bean

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-28
 */
data class CoinInfo(
    var coinCount: Int = 0,  // 当前积分
    var level: Int = 0,
    var nickname: String = "",
    var rank: String = "",
    var userId: Int = 0,
    var username: String = ""
)