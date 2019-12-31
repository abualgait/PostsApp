package com.abualgait.msayed.thiqah.ui.postsactivity

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.abualgait.msayed.thiqah.shared.data.DataManager
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO
import com.abualgait.msayed.thiqah.shared.util.ext.with
import com.abualgait.msayed.thiqah.shared.util.ext.withDB
import com.abualgait.msayed.thiqah.shared.vm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val postsActivityModule = module {
    viewModel { PostsActivityVm(get()) }
}

class PostsActivityVm(dataManager: DataManager) : BaseViewModel(dataManager) {


    var payload: MutableLiveData<ResultUIModel> = MutableLiveData()
    var editPayload: MutableLiveData<ResultUIModel> = MutableLiveData()
    var deletePayload: MutableLiveData<ResultUIModel> = MutableLiveData()
    var addPayload: MutableLiveData<ResultUIModel> = MutableLiveData()
    val showError = MutableLiveData<ResultUIModel>()


    private fun getPostsFromDatabase(): Observable<List<PostPOJO>> {
        return Observable.defer { Observable.just(database.getPostDao()!!.getItems()) }
            .withDB(scheduler)
    }

    private fun getPostsFromApi(): Observable<List<PostPOJO>> {
        return api.getPosts()
            .map {
                Observable.just(database)
                    .withDB(scheduler)
                    .subscribe { db ->
                        db.getPostDao()?.deletePosts()
                        db.getPostDao()?.insertALLItems(it as MutableList<PostPOJO>)
                    }

                it
            }
            .subscribeOn(Schedulers.io())

    }

    @SuppressLint("CheckResult")
    fun getPosts() {
        payload.value = ResultUIModel(isLoading = true)
        Observable.concat(
            getPostsFromDatabase(), getPostsFromApi()
        ).with(scheduler)
            .subscribe(object : Observer<List<PostPOJO>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(result: List<PostPOJO>) {
                    payload.value = ResultUIModel(list = result)
                    Observable.just(database)
                        .subscribeOn(Schedulers.io())
                        .subscribe { db ->
                            db.getPostDao()?.deletePosts()
                            db.getPostDao()?.insertALLItems(result as MutableList<PostPOJO>)
                        }
                }

                override fun onError(error: Throwable) {
                    payload.value = ResultUIModel(error = error)
                }
            })

    }


    @SuppressLint("CheckResult")
    fun editPost(id: String, postPOJO: PostPOJO) {
        launch {
            api.editPost(id, postPOJO).with(scheduler).subscribe(
                {
                    view.hideLoading()
                    editPayload.value = ResultUIModel(postPojo = it)

                }, {
                    view.hideLoading()
                    showError.value = ResultUIModel(error = it)
                })
        }

    }

    @SuppressLint("CheckResult")
    fun deletePost(id: String) {
        launch {
            api.deletePost(id).with(scheduler).subscribe(
                {
                    view.hideLoading()
                    deletePayload.value = ResultUIModel(postPojo = it)

                }, {
                    view.hideLoading()
                    showError.value = ResultUIModel(error = it)
                })

        }
    }

    @SuppressLint("CheckResult")
    fun addPost(postPOJO: PostPOJO) {
        launch {
            api.addPost(postPOJO).with(scheduler).subscribe(
                {
                    view.hideLoading()
                    addPayload.value = ResultUIModel(postPojo = it)

                }, {
                    view.hideLoading()
                    showError.value = ResultUIModel(error = it)
                })

        }
    }

    data class ResultUIModel(
        val postPojo: PostPOJO? = null,
        val list: List<PostPOJO> = emptyList(),
        val error: Throwable? = null,
        val isLoading: Boolean = false
    )

}