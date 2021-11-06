package com.tiyas.mygithubapp.data


import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("following")
    val following: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("login")
    val login: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("public_repos")
    val publicRepos: Int?,
)