package com.abualgait.msayed.thiqah.shared.network


import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO

import io.reactivex.Observable
import retrofit2.http.*


interface ApiInterface {


    @GET("posts")
    fun getPosts(): Observable<List<PostPOJO>>

    @PUT("posts/{id}")
    fun editPost(
            @Path("id") id: String,
            @Body postPOJO: PostPOJO): Observable<PostPOJO>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: String): Observable<PostPOJO>


    @POST("posts")
    fun addPost(
            @Body postPOJO: PostPOJO): Observable<PostPOJO>

    @GET("posts/{id}")
    fun getPostDetails(@Path("id") id: String): Observable<PostPOJO>


}