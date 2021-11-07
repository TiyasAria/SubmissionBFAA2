package com.tiyas.mygithubapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tiyas.mygithubapp.data.Favorite
import com.tiyas.mygithubapp.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository : FavoriteRepository?

    init {
        mFavoriteRepository = FavoriteRepository(application)
    }

    fun getAllFavorites() : LiveData<List<Favorite>>? = mFavoriteRepository?.getAllFavorites()
}