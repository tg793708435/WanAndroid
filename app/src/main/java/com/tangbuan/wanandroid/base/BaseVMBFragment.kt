package com.tangbuan.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tangbuan.wanandroid.BR
import com.tangbuan.wanandroid.R
import com.tangbuan.wanandroid.extension.hideLoading
import com.tangbuan.wanandroid.utils.LogUtil
import com.tangbuan.wanandroid.utils.ToastUtil
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @brief description
 * @author tangbuan
 * @date 2024-05-25
 */
abstract class BaseVMBFragment<VM : BaseViewModel, B : ViewDataBinding>(
    private val contentViewId: Int,
    private val VMClass: Class<VM>
) :
    Fragment() {
    /** 是否第一次加载 */
    private var mIsFirstLoading = true


    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: B


    // 初始化DataBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, contentViewId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsFirstLoading = true
        initViewModel()
        initView()
        setupDataBinding()
        createObserve()
    }

    // 初始化ViewModel
    private fun initViewModel() {
        /*// 这里利用反射获取泛型中第一个参数ViewModel
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this)[type]*/
        mViewModel = ViewModelProvider(this)[VMClass]
        mViewModel.start()
    }

    /** View相关初始化
     * 由子类自己实现
     * */
    abstract fun initView()

    /** DataBinding相关设置 */
    private fun setupDataBinding() {
        mBinding.apply {
            // 需绑定lifecycleOwner到Fragment,xml绑定的数据才会随着liveData数据源的改变而改变
            setLifecycleOwner(viewLifecycleOwner)
        }
    }

    open fun createObserve() {
        mViewModel.apply {
            exception.observe(viewLifecycleOwner) {
                requestError(it.message)
                LogUtil.e("网络请求错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        requireContext(),
                        getString(R.string.request_time_out)
                    )

                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        requireContext(),
                        getString(R.string.network_error)
                    )

                    else -> ToastUtil.showShort(
                        requireContext(), it.message ?: getString(R.string.response_error)
                    )
                }
            }

            // 全局服务器返回的错误信息监听
            errorResponse.observe(viewLifecycleOwner) {
                requestError(it?.errorMsg)
                it?.errorMsg?.run {
                    ToastUtil.showShort(requireContext(), this)
                }
            }
        }
    }

    /** 提供一个请求错误的方法,用于像关闭加载框之类的 */
    open fun requestError(msg: String? = null) {
        hideLoading()
    }

    override fun onResume() {
        super.onResume()
        if (mIsFirstLoading) {
            lazyLoadData()
            mIsFirstLoading = false
        }

    }

    /*数据懒加载，子类可以定义懒加载方式，在resume时调用*/
    abstract fun lazyLoadData()
}