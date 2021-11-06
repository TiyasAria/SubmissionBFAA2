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
import com.tiyas.mygithubapp.databinding.FragmentFollowingBinding
import com.tiyas.mygithubapp.ui.DetailActivity
import com.tiyas.mygithubapp.viewModel.FollowingViewModel


class Following : Fragment() {

    private var _followingBinding : FragmentFollowingBinding? = null
    private  val followingBinding get() = _followingBinding!!
    private  lateinit var  followingViewModel: FollowingViewModel
    private lateinit var  adapter: UserAdapter
    private  lateinit var  username : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _followingBinding = FragmentFollowingBinding.inflate(inflater, container, false)
        return followingBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        followingBinding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter

        }

        showLoading(true)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)
        followingViewModel.setListFollowing(username)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setData(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _followingBinding = null
    }

    private fun showLoading(b: Boolean) {
        followingBinding.apply {
            if(b){
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }


}