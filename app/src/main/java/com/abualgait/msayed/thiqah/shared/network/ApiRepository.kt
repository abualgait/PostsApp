package com.abualgait.msayed.thiqah.shared.network


import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO
import io.reactivex.Observable


class ApiRepository(private val api: ApiInterface) {

    fun getPosts(): Observable<List<PostPOJO>> {
        return api.getPosts()
    }

    fun editPost(id: String, postPOJO: PostPOJO): Observable<PostPOJO> {
        return api.editPost(id, postPOJO)
    }

    fun deletePost(id: String): Observable<PostPOJO> {
        return api.deletePost(id)
    }

    fun addPost(postPOJO: PostPOJO): Observable<PostPOJO> {
        return api.addPost(postPOJO)
    }

    fun getPostDetails(id: String): Observable<PostPOJO> {
        return api.getPostDetails(id)
    }
}
