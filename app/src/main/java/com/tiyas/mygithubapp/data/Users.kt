package com.tiyas.mygithubapp.data


import com.google.gson.annotations.SerializedName

data class Users(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("company")
    val company: String? = null,
    @SerializedName("followers")
    val followers: Int? = null ,
    @SerializedName("following")
    val following: Int? = null ,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("public_repos")
    val publicRepos: Int? = null,
)