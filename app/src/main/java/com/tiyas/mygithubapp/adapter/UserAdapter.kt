package com.tiyas.mygithubapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tiyas.mygithubapp.R
import com.tiyas.mygithubapp.data.Users
import com.tiyas.mygithubapp.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val  users = ArrayList<Users>()
    private var onItemClickCallback : OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClick(data : Users)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: ArrayList<Users>){
        users.clear()
        users.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemUserBinding = ItemUserBinding.bind(itemView)

        fun bind(users: Users){
            Glide.with(itemView.context)
                .load(users.avatarUrl)
                .apply(
                    RequestOptions()
                        .override(80, 80)
                    )
                .into(itemUserBinding.ivProfile)

            itemUserBinding.tvUsername.text = users.login
            itemUserBinding.tvName.text = users.name

            itemView.setOnClickListener{
               onItemClickCallback?.onItemClick(users)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return  UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

}