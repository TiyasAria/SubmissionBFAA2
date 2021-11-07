package com.tiyas.mygithubapp.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiyas.mygithubapp.api.RetrofitClient
import com.tiyas.mygithubapp.data.Favorite
import com.tiyas.mygithubapp.data.Users
import com.tiyas.mygithubapp.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(val application: Application) : ViewModel() {
    val listUsers = MutableLiveData<Users>()
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun setDataUser(username: String){
        RetrofitClient
            .apiInstance()
            .getDetailUser(username)
            .enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                   if(response.isSuccessful){
                       listUsers.postValue(response.body())
                   }
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    Log.d("onFailure", "Data errorr ")
                    Toast.makeText(
                        application.applicationContext,
                        t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun getDetailUser() : LiveData<Users>{
        return listUsers
    }

    fun checkUser(id : Int) = mFavoriteRepository.check(id)

    fun addToFavorite(username: String, id: Int, avatarUrl : String?, name: String?){
        CoroutineScope(Dispatchers.IO).launch {
            val user = Favorite(
                id, avatarUrl, username, name
            )
            mFavoriteRepository.insert(user)
        }
    }

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mFavoriteRepository.delete(id)
        }
    }

}