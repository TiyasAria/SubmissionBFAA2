package com.tiyas.mygithubapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiyas.mygithubapp.R
import com.tiyas.mygithubapp.adapter.UserAdapter
import com.tiyas.mygithubapp.data.Users
import com.tiyas.mygithubapp.databinding.ActivityMainBinding
import com.tiyas.mygithubapp.helper.ViewModelFactory
import com.tiyas.mygithubapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var  mainBinding : ActivityMainBinding
    private  lateinit var  mainViewModel : MainViewModel
    private lateinit var  userAdapter : UserAdapter
    private var isChecked  : Boolean = false


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        supportActionBar?.title = resources.getString(R.string.app)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClick(data: Users) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    it.putExtra(DetailActivity.EXTRA_NAME, data.name)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }

        })

       mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getThemeSettings().observe(this,{ isDarkModeActive : Boolean ->
            if(isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        })

        searchUser("t")
        mainBinding.apply {
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.setHasFixedSize(true)
            rvMain.adapter = userAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                   searchUser(query)
                    return true
                }

                override fun onQueryTextChange(newText : String): Boolean {
                    if(newText.isEmpty()){
                        searchUser("t")
                    } else{
                       searchUser(newText)
                    }
                    return false
                }

            })
        }

        mainViewModel.getSearchUsers().observe(this, {
            if (it != null){
                userAdapter.setData(it)
                showLoading(false)
            }
        })

    }

    private fun searchUser(query : String){
        showLoading(true)
        mainViewModel.setSearchUsers(query)
    }

    private fun obtainViewModel(activity: AppCompatActivity) : MainViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if(isChecked) {
            menu.findItem(R.id.theme_mode).setIcon(R.drawable.ic_light_mode)
        } else{
            menu.findItem(R.id.theme_mode).setIcon(R.drawable.ic_dark_mode)
        }
        return  super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
            R.id.theme_mode  -> {
                isChecked = !isChecked
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Theme")
                builder.setMessage("Light/Dark")
                builder.setPositiveButton("Activate") {
                    _, _ -> mainViewModel.saveThemeSetting(isChecked)
                }
                builder.setNegativeButton("Cancel") {
                    dialog, _ -> dialog.cancel()
                }
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}