/*
 * Created by Alican Sekban on 24.03.2023 16:07
 * Copyright © 2023 Alican Sekban. All rights reserved.
 */
package com.alican.mvvm_starter.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import coil.request.Disposable
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.util.utils.customview.CustomDialog
abstract class BaseActivity<VDB : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: VDB
    private lateinit var mCustomDialog: CustomDialog
    lateinit var bottomSheetDialog: com.alican.mvvm_starter.util.utils.BottomSheetDialog
    var isCheckInternetActive = true
    private var connectivityDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCustomDialog = CustomDialog(this, R.style.LoadingDialogStyle)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    @Suppress("SameParameterValue")
    fun showBottomSheet(
        context: Context,
        title: Int,
        listener: com.alican.mvvm_starter.util.utils.BottomSheetDialog.BottomSheetListener,
    ) {
        bottomSheetDialog = com.alican.mvvm_starter.util.utils.BottomSheetDialog.instance.apply {
            setupSheet(context.getString(title))
            this.listener = listener
        }.also {
            it.show(supportFragmentManager, getString(title))
        }
    }

    fun showProgressDialog() {
        try {
            if (!mCustomDialog!!.isShowing) {
                mCustomDialog!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressDialog() {
        try {
            if (mCustomDialog!!.isShowing)
                mCustomDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Dispose disposables when screen destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        connectivityDisposable?.let { if (!it.isDisposed) it.dispose() }
    }

}