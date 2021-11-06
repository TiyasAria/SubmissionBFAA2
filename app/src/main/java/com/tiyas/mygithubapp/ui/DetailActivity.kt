package com.tiyas.mygithubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.tiyas.mygithubapp.R
import com.tiyas.mygithubapp.adapter.SectionPagerAdapter
import com.tiyas.mygithubapp.databinding.ActivityDetailBinding
import com.tiyas.mygithubapp.viewModel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var  detailViewModel : DetailViewModel


    companion object{
        const val EXTRA_USERNAME = "extra_username"
        private val TITLE_TABS = intArrayOf(R.string.followers, R.string.following)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.hide()

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        detailViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

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

                }
            }
            )
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        detailBinding.apply {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(detailBinding.tabs, detailBinding.viewPager){ tab,position ->
                tab.text = resources.getString(TITLE_TABS[position])
            }.attach()

        }


    }
}