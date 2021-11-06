package com.tiyas.mygithubapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiyas.mygithubapp.api.RetrofitClient
import com.tiyas.mygithubapp.data.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val listUsers = MutableLiveData<Users>()

    fun setDataUser(username: String){
        RetrofitClient
            .apiInstance()
            .getDetailUser(username)
            .enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                   if(response.isSuccessful){
                       val responseBody = response.body()
                       listUsers.postValue(responseBody)
                   }
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    Log.d("onFailure", "Data errorr ")
                }
            })
    }

    fun getDetailUser() : LiveData<Users>{
        return listUsers
    }
}