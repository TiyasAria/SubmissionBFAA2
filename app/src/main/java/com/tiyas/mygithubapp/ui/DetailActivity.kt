package com.tiyas.mygithubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.tiyas.mygithubapp.R
import com.tiyas.mygithubapp.adapter.SectionPagerAdapter
import com.tiyas.mygithubapp.databinding.ActivityDetailBinding
import com.tiyas.mygithubapp.helper.ViewModelFactory
import com.tiyas.mygithubapp.viewModel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var  detailViewModel : DetailViewModel


    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_NAME = "extra_name"
        private val TITLE_TABS = intArrayOf(R.string.followers, R.string.following)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.title = "Detail User"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl  = intent.getStringExtra(EXTRA_AVATAR)
        val name = intent.getStringExtra(EXTRA_NAME)


        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

      detailViewModel = obtainViewModel(this@DetailActivity)

        if (username != null){
            detailViewModel.setDataUser(username)
            detailViewModel.getDetailUser().observe(this, {
                detailBinding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .apply (
                               RequestOptions.circleCropTransform()
                                .placeholder(R.drawable.ic_launcher_background)
                                )
                        .into(ivProfileDetail)

                    tvNameDetail.text = it.name ?: getString(R.string.name)
                    tvUsernameDetail.text = it.login
                    tvCompany.text = it.company ?: "_"
                    tvLocation.text = it.location ?: "_"
                    tvRepository.text = it.publicRepos.toString()
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()

                    var isFavorite = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val count = detailViewModel.checkUser(id)
                        withContext(Dispatchers.Main){
                            if (count > 0){
                                setStatusFavorite(true)
                                isFavorite = true
                            } else {
                                setStatusFavorite(false)
                            }
                        }
                    }

                    fabFavorite.setOnClickListener {
                        isFavorite = ! isFavorite

                        if (isFavorite){
                                detailViewModel.addToFavorite(username, id, avatarUrl, name)
                                Toast.makeText(
                                    this@DetailActivity,
                                    getString(R.string.toast_add_favorite),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else{
                                detailViewModel.removeFromFavorite(id)
                                Toast.makeText(
                                    this@DetailActivity,
                                    getString(R.string.remove_from_favorite),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        setStatusFavorite(isFavorite)
                        }

                    }

            })
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        detailBinding.apply {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(detailBinding.tabs, detailBinding.viewPager){ tab,position ->
                tab.text = resources.getString(TITLE_TABS[position])
            }.attach()

        }


    }

    private  fun setStatusFavorite(isFavorite : Boolean){
        if(isFavorite){
            detailBinding.fabFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            detailBinding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private  fun obtainViewModel(activity: AppCompatActivity) : DetailViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }
}