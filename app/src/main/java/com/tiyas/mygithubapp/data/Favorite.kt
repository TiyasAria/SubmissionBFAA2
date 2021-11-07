package com.tiyas.mygithubapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0 ,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl : String? = null ,

    @ColumnInfo(name = "username")
    var username : String? = null,

    @ColumnInfo(name = "name")
    var name : String? = null

        )