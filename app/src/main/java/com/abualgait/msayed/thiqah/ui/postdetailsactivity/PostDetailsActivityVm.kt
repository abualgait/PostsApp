package com.abualgait.msayed.thiqah.ui.postdetailsactivity

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abualgait.msayed.thiqah.shared.data.DataManager
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO
import com.abualgait.msayed.thiqah.shared.util.ext.with
import com.abualgait.msayed.thiqah.shared.vm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val postDetailsActivityModule = module {
    viewModel { PostDetailsActivityVm(get()) }
}

class PostDetailsActivityVm(dataManager: DataManager) : BaseViewModel(dataManager) {


    var postDetailsPOJO: MutableLiveData<PostPOJO> = MutableLiveData()
    private val showError = MutableLiveData<String>()


    fun getPostDetailsLiveData(): LiveData<PostPOJO> {
        return postDetailsPOJO
    }

    fun getError(): LiveData<String> {
        return showError
    }


    @SuppressLint("CheckResult")
    fun getPostDetailsFromDatabase(id: String) {
        Observable.defer { Observable.just(database.getPostDao()!!.getItemById(id.toInt())) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    view.hideLoading()
                    postDetailsPOJO.value = result
                }, { error ->
                    view.hideLoading()
                    showError.value = error.message
                })

    }

    @SuppressLint("CheckResult")
    fun getPostDetailsApi(id: String) {
        //view.showLoading()
        api.getPostDetails(id).with(scheduler).subscribe({
           // view.hideLoading()
            postDetailsPOJO.value = it
        }, {
            //view.hideLoading()
            showError.value = it.message
        })


    }


}