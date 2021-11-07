package com.tiyas.mygithubapp.api

import com.tiyas.mygithubapp.BuildConfig
import com.tiyas.mygithubapp.data.UserResponse
import com.tiyas.mygithubapp.data.Users
import retrofit2.Call
import retrofit2.http.*

interface ApiService{

    companion object{
        const val apikey = BuildConfig.Api_key
    }

    @GET("search/users")
    @Headers("Authorization: $apikey")
    fun getSearchUser(
        @Query("q") q:String
    )  : Call<UserResponse>


    @GET("users/{username}")
    @Headers("Authorization: $apikey")
    fun getDetailUser(
        @Path("username") username : String
    )  : Call<Users>

    @GET("users/{username}/followers")
    @Headers("Authorization: $apikey")
    fun getUserFollower(
        @Path("username") username: String
    ) : Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: $apikey")
    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<Users>>

}