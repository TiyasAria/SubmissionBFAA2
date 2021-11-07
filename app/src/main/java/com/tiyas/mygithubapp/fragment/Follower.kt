package com.tiyas.mygithubapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiyas.mygithubapp.adapter.UserAdapter
import com.tiyas.mygithubapp.databinding.FragmentFollowerBinding
import com.tiyas.mygithubapp.ui.DetailActivity
import com.tiyas.mygithubapp.viewModel.FollowerViewModel


class Follower : Fragment() {

    private var _followerBinding : FragmentFollowerBinding? = null
    private val followerBinding get() = _followerBinding!!
    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _followerBinding = FragmentFollowerBinding.inflate(inflater, container, false )
        return followerBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        followerBinding.apply {
            rvFollower.layoutManager = LinearLayoutManager(activity)
            rvFollower.setHasFixedSize(true)
            rvFollower.adapter = adapter
        }

        showLoading(true)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]
        followerViewModel.setListFollower(username)
        followerViewModel.getListFollower().observe(viewLifecycleOwner, {
            if(it != null){
                adapter.setData(it)
                showLoading(false)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _followerBinding = null
    }

    private fun showLoading(b: Boolean) {
        followerBinding.apply {
            if(b){
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }



}