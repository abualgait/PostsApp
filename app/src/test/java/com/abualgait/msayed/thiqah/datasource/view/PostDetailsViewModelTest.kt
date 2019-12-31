package com.abualgait.msayed.thiqah.datasource.view

import androidx.lifecycle.Observer
import com.abualgait.msayed.thiqah.datasource.BaseUnitTest
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO
import com.abualgait.msayed.thiqah.shared.network.ApiRepository
import com.abualgait.msayed.thiqah.ui.postdetailsactivity.PostDetailsActivityVm
import com.abualgait.msayed.thiqah.ui.postsactivity.PostsActivityVm
import junit.framework.Assert
import org.junit.Test
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito

class PostDetailsViewModelTest : BaseUnitTest() {

    val repository by inject<ApiRepository>()
    private val postViewModel: PostDetailsActivityVm by inject()

    @Mock
    lateinit var uiData: Observer<PostPOJO>


   @Test
    fun testGetPostDetails() {
       repository.getPosts().test()
       val list = repository.getPosts().blockingFirst()
        postViewModel.postDetailsPOJO.observeForever(uiData)
       val first = list.first()
        postViewModel.getPostDetailsApi(first.id.toString())
       // Has been notified
       Mockito.verify(uiData).onChanged(first)


    }
}