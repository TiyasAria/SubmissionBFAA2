package com.tiyas.mygithubapp.data


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("items")
    val items: ArrayList<Users>?,
)