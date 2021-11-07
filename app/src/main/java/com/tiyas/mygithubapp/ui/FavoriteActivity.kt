package com.tiyas.mygithubapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiyas.mygithubapp.adapter.UserAdapter
import com.tiyas.mygithubapp.data.Favorite
import com.tiyas.mygithubapp.data.Users
import com.tiyas.mygithubapp.databinding.ActivityFavoriteBinding
import com.tiyas.mygithubapp.helper.ViewModelFactory
import com.tiyas.mygithubapp.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private  lateinit var favoriteBinding: ActivityFavoriteBinding
    private  lateinit var  favoriteViewModel: FavoriteViewModel
    private  lateinit var  adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        supportActionBar?.title = "Favorite Github"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClick(data: Users) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_NAME, data.name)
                    startActivity(it)
                }
            }

        })

        favoriteBinding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = adapter
        }

        favoriteViewModel.getAllFavorites()?.observe(this, {favoriteList ->
            if(favoriteList != null){
                val list = mapList(favoriteList)
                adapter.setData(list)
            }
        })

    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun mapList(listFavorites : List<Favorite>) : ArrayList<Users>{
        val listUser = ArrayList<Users>()
        for(user in listFavorites){
            val userMapped = Users(
                login = user.username,
                id = user.id,
                name =  user.name,
                avatarUrl =  user.avatarUrl,
            )
            listUser.add(userMapped)
        }
        return  listUser
    }
}