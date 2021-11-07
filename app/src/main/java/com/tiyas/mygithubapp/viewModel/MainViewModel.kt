package com.tiyas.mygithubapp.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.tiyas.mygithubapp.api.RetrofitClient
import com.tiyas.mygithubapp.data.UserResponse
import com.tiyas.mygithubapp.data.Users
import com.tiyas.mygithubapp.preferences.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val application: Application) : ViewModel() {
    private val Context.dataStore : DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")
     private val pref: SettingPreferences = SettingPreferences.getInstance(application.dataStore)
    val listUsers = MutableLiveData<ArrayList<Users>>()

//    make a function for setSearchUsers
    fun setSearchUsers( query : String ){
    RetrofitClient.apiInstance()
        .getSearchUser(query)
        .enqueue( object  : Callback<UserResponse>{
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        listUsers.postValue(response.body()!!.items!!)
                    }
                    Log.e("Data", "onResponse: ${response.body()?.items}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
                Toast.makeText(
                    application.applicationContext,
                    t.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getSearchUsers() : LiveData<ArrayList<Users>>{
        return listUsers
    }

    fun getThemeSettings() : LiveData<Boolean>{
        return  pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive : Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}


