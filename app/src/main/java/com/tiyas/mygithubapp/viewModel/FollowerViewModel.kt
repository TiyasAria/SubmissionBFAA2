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

class FollowerViewModel : ViewModel() {

    val listFollower = MutableLiveData<ArrayList<Users>>()

    fun setListFollower(username : String){
        RetrofitClient.apiInstance().getUserFollower(username)
            .enqueue(object : Callback<ArrayList<Users>>{
                override fun onResponse(
                    call: Call<ArrayList<Users>>,
                    response: Response<ArrayList<Users>>
                ) {
                    if (response.isSuccessful){
                        val responseBody = response.body()
                        listFollower.postValue(responseBody)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getListFollower() : LiveData<ArrayList<Users>>{
        return listFollower
    }
}