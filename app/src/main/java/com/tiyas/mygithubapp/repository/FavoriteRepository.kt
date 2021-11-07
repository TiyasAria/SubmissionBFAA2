package com.tiyas.mygithubapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.tiyas.mygithubapp.data.Favorite
import com.tiyas.mygithubapp.database.FavoriteDao
import com.tiyas.mygithubapp.database.FavoriteRoomDatabase

class FavoriteRepository(application: Application){
    private val mFavoriteDao : FavoriteDao

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites() : LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun check(id:Int) = mFavoriteDao.check(id)

    fun insert(favorite: Favorite) = mFavoriteDao.insert(favorite)

    fun delete(id: Int) = mFavoriteDao.delete(id)

}