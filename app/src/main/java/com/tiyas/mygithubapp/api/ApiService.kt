package com.tiyas.mygithubapp.api

import com.tiyas.mygithubapp.BuildConfig
import com.tiyas.mygithubapp.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    companion object{
        const val apikey = BuildConfig.Api_key
    }
//     untuk mendapatkan seaarch nya
    @GET("search/users")
    @Headers("Authorization: token $apikey ")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<UserResponse>


    @GET("users/{username}")
    @Headers("Authorization: token $apikey")
    fun getDetailUser(
        @Path("username") username : String
    )  : Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $apikey")
    fun getUserFollower(
        @Path("username") username: String
    ) : Call<ArrayList<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $apikey")
    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<UserResponse>>

}