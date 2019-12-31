package com.abualgait.msayed.thiqah.shared.vm


import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.abualgait.msayed.thiqah.shared.data.DataManager
import com.abualgait.msayed.thiqah.shared.databases.DBRepository
import com.abualgait.msayed.thiqah.shared.network.ApiRepository
import com.abualgait.msayed.thiqah.shared.rx.SchedulerProvider
import com.abualgait.msayed.thiqah.shared.ui.view.BaseView
import com.abualgait.msayed.thiqah.shared.util.SharedPref
import com.abualgait.msayed.thiqah.shared.util.io.app.MyApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(dm: DataManager)
    : ViewModel() {

    lateinit var view: BaseView
    var pref: SharedPref = dm.pref
    var api: ApiRepository = dm.api
    var database: DBRepository = dm.database
    var scheduler: SchedulerProvider = dm.schedulerProvider


    val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun activity(): FragmentActivity? {
        return view.activity()
    }


    protected fun getString(@StringRes res: Int): String {
        return MyApp.context.getString(res)
    }


    fun loading(isLoading: Boolean) {
        if (isLoading) {
            view.showLoading()
            return
        }
        view.hideLoading()
    }


}
