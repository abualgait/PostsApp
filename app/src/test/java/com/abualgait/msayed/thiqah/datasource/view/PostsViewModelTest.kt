package com.abualgait.msayed.thiqah.datasource.view

import androidx.lifecycle.Observer
import com.abualgait.msayed.thiqah.datasource.BaseUnitTest
import com.abualgait.msayed.thiqah.shared.network.ApiRepository
import com.abualgait.msayed.thiqah.ui.postsactivity.PostsActivityVm
import io.reactivex.Observable
import org.junit.Test
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito

class PostsViewModelTest : BaseUnitTest() {

    val repository by inject<ApiRepository>()
    private val postViewModel: PostsActivityVm by inject()

    @Mock
    lateinit var listObserver: Observer<PostsActivityVm.ResultUIModel>


    @Test
    fun testGetPosts() {
        repository.getPosts().blockingFirst()

        postViewModel.payload.observeForever(listObserver)
        postViewModel.getPosts()
        Mockito.verify(listObserver).onChanged(PostsActivityVm.ResultUIModel(isLoading = true))

        val value = postViewModel.payload.value ?: error("No value for view myModel")
        //give me an error -->  Method getMainLooper in android.os.Looper not mocked
        Mockito.verify(listObserver).onChanged(PostsActivityVm.ResultUIModel(list = value.list))

    }

    @Test
    fun testGetPostsFaild() {
        postViewModel.payload.observeForever(listObserver)
        postViewModel.getPosts()

        Mockito.verify(listObserver).onChanged(PostsActivityVm.ResultUIModel(isLoading = true))


        val error = IllegalStateException("Got an error !")
        Mockito.`when`(repository.getPosts()).thenReturn(Observable.error(error))

        Mockito.verify(listObserver).onChanged(PostsActivityVm.ResultUIModel(error = error))


    }
}