package com.tiyas.mygithubapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiyas.mygithubapp.adapter.UserAdapter
import com.tiyas.mygithubapp.data.Users
import com.tiyas.mygithubapp.databinding.ActivityMainBinding
import com.tiyas.mygithubapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var  mainBinding : ActivityMainBinding
    private  lateinit var  mainViewModel : MainViewModel
    private lateinit var  userAdapter : UserAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        supportActionBar?.hide()

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClick(data: Users) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getSearchUsers().observe(this,{
            if (it != null){
                userAdapter.setData(it)
                showLoading(false)
                mainBinding.imgGithub.visibility = View.GONE

            }
        })

        mainBinding.apply {
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.setHasFixedSize(true)
            rvMain.adapter = userAdapter

            imgGithub.visibility = View.VISIBLE
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.setSearchUsers(query)
                    imgGithub.visibility = View.GONE
                    showLoading(true)
                    return true
                }

                override fun onQueryTextChange(newText : String): Boolean {
                    if(newText.isEmpty()){
                        mainViewModel.setSearchUsers(newText)
                        showLoading(false)
                        Toast.makeText(this@MainActivity, "User Tidak ditemukan", Toast.LENGTH_SHORT).show()
                    } else{
                        mainViewModel.setSearchUsers(newText)
                        showLoading(true)
                    }
                    return false
                }

            })
        }

    }

    private fun showLoading(b: Boolean) {
        mainBinding.apply {
            if (b){
                progressBar.visibility = View.VISIBLE
            } else{
                progressBar.visibility = View.GONE
            }
        }
    }

}