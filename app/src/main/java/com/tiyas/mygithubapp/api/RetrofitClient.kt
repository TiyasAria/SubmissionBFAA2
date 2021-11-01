package com.tiyas.mygithubapp.api

import com.google.gson.GsonBuilder
import com.tiyas.mygithubapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient  {

//     untuk menampilkan body nya di log , menggunakan Http logging
private val interceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

//    untuk menghubungkan ke client
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

//    func untuk menyambungkan ke endpoint dari class ai service kita
    fun apiInstance() : ApiService = retrofit.create(
    ApiService::class.java
    )

}