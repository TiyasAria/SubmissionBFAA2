package com.tiyas.mygithubapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tiyas.mygithubapp.fragment.Follower
import com.tiyas.mygithubapp.fragment.Following

class SectionPagerAdapter(activity: AppCompatActivity, data : Bundle) : FragmentStateAdapter(activity){

    private val fragmentBundle : Bundle = data

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = Follower()
            1 -> fragment = Following()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

}