package com.tangbuan.wanandroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.tangbuan.wanandroid.BR
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.extension.hideLoading
import com.tangbuan.wanandroid.utils.LogUtil
import com.tangbuan.wanandroid.utils.ToastUtil
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-25
 */
abstract class BaseVMBActivity<VM : BaseViewModel, B : ViewDataBinding>(
    private val contentViewResId: Int,
    private val VMClass: Class<VM>
) : AppCompatActivity() {
    lateinit var mViewModel: VM
    lateinit var mBinding: B

    /** 是否是无状态栏的全屏模式 */
    open fun setFullScreen(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置沉浸式状态栏，由于启动页SplashActivity需要无状态栏，这里写死不太好
        // 直接在主题里将其他的状态栏颜色写成跟ActionBar相同，而启动页则是无状态栏
        // 或者提供一个修改的api让SplashActivity重写，两者均可（假如需要更换主题用代码设置更灵活）
        // TODO 设置沉浸式状态栏
        /*if (setFullScreen()) {
            StatusBarUtil.setNoStatus(this)
        } else {
            StatusBarUtil.setImmersionStatus(this)
        }*/

        initViewModel()
        initDataBinding()
        createObserve()
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    open fun createObserve() {
        // 全局服务器请求错误监听
        mViewModel.apply {
            exception.observe(this@BaseVMBActivity) {
                requestError(it.message)
                LogUtil.e("网络请求错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        this@BaseVMBActivity,
                        getString(R.string.request_time_out)
                    )

                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        this@BaseVMBActivity,
                        getString(R.string.network_error)
                    )

                    else -> ToastUtil.showShort(
                        this@BaseVMBActivity, it.message ?: getString(R.string.response_error)
                    )
                }
            }

            // 全局服务器返回的错误信息监听
            errorResponse.observe(this@BaseVMBActivity) {
                requestError(it?.errorMsg)
                it?.errorMsg?.run {
                    ToastUtil.showShort(this@BaseVMBActivity, this)
                }
            }
        }
    }

    private fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, contentViewResId)
        mBinding.apply {
            // 需绑定lifecycleOwner到activity,xml绑定的数据才会随着liveData数据源的改变而改变
            lifecycleOwner = this@BaseVMBActivity
            // TODO setVariable(BR.viewModel, mViewModel)  动态设置variable
//            setVariable(BR.article, mViewModel)
        }
    }

    private fun initViewModel() {
        /*
        // 这里利用反射获取泛型中第一个参数ViewModel
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this)[type]
        mViewModel.start()*/
        mViewModel = ViewModelProvider(this)[VMClass]
        mViewModel.start()
    }

    /** 提供一个请求错误的方法,用于像关闭加载框,显示错误布局之类的 */
    open fun requestError(msg: String?) {
        hideLoading()
    }
}