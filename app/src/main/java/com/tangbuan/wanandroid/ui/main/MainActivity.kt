package com.tangbuan.wanandroid.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.base.BaseActivity
import com.tangbuan.wanandroid.databinding.ActivityMainBinding
import com.tangbuan.wanandroid.ui.main.home.HomeFragment
import com.tangbuan.wanandroid.ui.main.mine.MineFragment
import com.tangbuan.wanandroid.ui.main.project.ProjectFragment
import com.tangbuan.wanandroid.ui.main.square.SquareFragment
import com.tangbuan.wanandroid.ui.main.wechat.WechatFragment
import com.tangbuan.wanandroid.utils.LogUtil
import com.tangbuan.wanandroid.utils.ToastUtil

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    R.layout.activity_main,
    MainViewModel::class.java
) {


    companion object {
        /** 记录修改配置（如页面旋转）前navBar的位置的常量 */
        private const val CURRENT_NAV_POSITION = "currentNavPosition"

        /** 跳转 */
        fun launch(context: Context?) {
            context?.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private val mHomeFragment by lazy { HomeFragment.newInstance() }

    private val mProjectFragment by lazy { ProjectFragment.newInstance() }
    private val mWechatFragment by lazy { WechatFragment.newInstance() }
    private val mSquareFragment by lazy { SquareFragment.newInstance() }
    private val mMineFragment by lazy { MineFragment.newInstance() }

    /** 当前显示的Fragment(默认开始为首页) */
    private var mCurrentFragment: Fragment = mHomeFragment
    private var mCurrentNavPosition = 0

    override fun initView(savedInstanceState: Bundle?) {
        // 添加fragment事务
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, mHomeFragment, "HomeFragment")
            .commitAllowingStateLoss()

        // 修改配置时（例如页面旋转、深浅模式切换等）的页面恢复
        if (savedInstanceState != null) {
            when (savedInstanceState.getInt(CURRENT_NAV_POSITION)) {
                0 -> R.id.menu_home
                1 -> R.id.menu_project
                2 -> R.id.menu_square
                3 -> R.id.menu_wechat
                else -> R.id.menu_mine
            }.let {
                mBinding.bottomNavigationView.selectedItemId = it
                onNavBarItemSelected(it)
            }
        }

        // 导航Tab
        mBinding.bottomNavigationView.apply {
            // 处理bottomNavigationView的item长按出现Toast的问题
            /*clearLongClickToast(
                mutableListOf(
                    R.id.menu_home,
                    R.id.menu_project,
                    R.id.menu_square,
                    R.id.menu_wechat,
                    R.id.menu_mine
                )
            )*/

            setOnItemSelectedListener {
                return@setOnItemSelectedListener onNavBarItemSelected(it.itemId)
            }
        }

        // 返回处理
        onBackPressedDispatcher.addCallback(this, mBackPressedCallback)
    }

    /**
     * bottomNavigationView切换时调用的方法
     *
     * @param itemId 切换Item的id
     */
    private fun onNavBarItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.menu_home -> {
                mCurrentNavPosition = 0
                switchFragment(mHomeFragment)
                return true
            }

            R.id.menu_project -> {
                mCurrentNavPosition = 1
                switchFragment(mProjectFragment)
                return true
            }

            R.id.menu_wechat -> {
                mCurrentNavPosition = 3
                switchFragment(mWechatFragment)
                return true
            }

            R.id.menu_square -> {
                mCurrentNavPosition = 2
                switchFragment(mSquareFragment)
                return true
            }


            else -> {
                mCurrentNavPosition = 4
                switchFragment(mMineFragment)
                return true
            }

        }
    }

    /**
     * 切换Fragment
     *
     * @param fragment 要切换的Fragment
     */
    private fun switchFragment(fragment: Fragment) {
        if (fragment !== mCurrentFragment) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()  // 获取事务
            // 先隐藏当前显示的Fragment
            fragmentTransaction.hide(mCurrentFragment)
            if (!fragment.isAdded) {
                LogUtil.d("mainActivity 启动 ${fragment.javaClass.simpleName}")
                // 存入Tag,以便获取，解决界面重叠问题 参考http://blog.csdn.net/showdy/article/details/50825800
                fragmentTransaction.add(R.id.fl_container, fragment, fragment.javaClass.simpleName)
                    .show(fragment)
            } else {
                fragmentTransaction.show(fragment)
            }
            // 执行提交
            fragmentTransaction.commitAllowingStateLoss()
            // 将当前Fragment赋值为切换后的Fragment
            mCurrentFragment = fragment
        }
    }

    /** 上一次点击返回键的时间 */
    private var lastBackMills: Long = 0

    /** 返回监听回调 */
    private val mBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - lastBackMills > 2000) {
                lastBackMills = System.currentTimeMillis()

                ToastUtil.showShort(
                    this@MainActivity,
                    getString(R.string.toast_double_back_exit)
                )
            } else {
                finish()
            }
        }
    }

}