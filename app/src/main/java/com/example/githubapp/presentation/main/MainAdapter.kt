package com.example.githubapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ItemUserBinding
import com.example.githubapp.model.User

class MainAdapter(private val onItemClick: (item: User) -> Unit) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val userItemList = mutableListOf<User>()

    fun addItems(userItemList: List<User>) {
        this.userItemList.clear()
        this.userItemList.addAll(userItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemUserBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemUserBinding)
    }

    override fun getItemCount(): Int = userItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = userItemList[position]
        holder.bind(userItem)
        holder.itemView.setOnClickListener { onItemClick(userItem) }
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                Glide.with(binding.root)
                    .load(user.avatarUrl)
                    .into(imgUser)
                tvUsername.text = user.login
            }
        }
    }

}