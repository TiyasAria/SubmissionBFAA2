package com.tiyas.mygithubapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiyas.mygithubapp.api.RetrofitClient
import com.tiyas.mygithubapp.data.Item
import com.tiyas.mygithubapp.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
     val listUsers = MutableLiveData<ArrayList<Item>>()

//    make a function for setSearchUsers
    fun setSearchUsers( query : String ){
    RetrofitClient.apiInstance()
        .getSearchUsers(query)
        .enqueue( object  : Callback<UserResponse>{
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body()?.items
                    listUsers.postValue(result)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getSearchUsers() : LiveData<ArrayList<Item>>{
        return  listUsers
    }

}


