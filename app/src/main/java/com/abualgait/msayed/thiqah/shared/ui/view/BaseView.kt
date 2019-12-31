package com.abualgait.msayed.thiqah.shared.ui.view


import android.content.Context
import com.abualgait.msayed.thiqah.shared.ui.activity.BaseActivity
import com.abualgait.msayed.thiqah.shared.vm.BaseViewModel

interface BaseView {


    fun baseViewModel(): BaseViewModel?

    fun activity(): BaseActivity<*>?

    fun context(): Context? {
        return activity()
    }

    fun showLoading() {}

    fun hideLoading() {}


}
