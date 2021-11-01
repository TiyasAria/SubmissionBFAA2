package com.tiyas.mygithubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tiyas.mygithubapp.R

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}