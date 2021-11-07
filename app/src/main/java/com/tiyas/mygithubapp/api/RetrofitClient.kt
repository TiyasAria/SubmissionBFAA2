package com.tiyas.mygithubapp.api

import com.google.gson.GsonBuilder
import com.tiyas.mygithubapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient  {


private val interceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)


private val client = OkHttpClient.Builder().addInterceptor(interceptor)
    .retryOnConnectionFailure(true)
    .connectTimeout(30, TimeUnit.SECONDS)
    .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    fun apiInstance() : ApiService = retrofit.create(
    ApiService::class.java
    )

}