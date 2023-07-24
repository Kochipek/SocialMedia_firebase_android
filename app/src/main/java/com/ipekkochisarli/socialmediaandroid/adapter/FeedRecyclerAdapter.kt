package com.ipekkochisarli.socialmediaandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipekkochisarli.socialmediaandroid.databinding.RecyclerRowBinding
import com.ipekkochisarli.socialmediaandroid.model.Post

class FeedRecyclerAdapter(private val postArrayList: ArrayList<Post>) :
    RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {
    class PostHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    // create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.textViewUserEmail.text = postArrayList[position].email
        holder.binding.textViewCaption.text = postArrayList[position].comment
        Glide.with(holder.binding.imageViewPicture).load(postArrayList[position].downloadUrl)
            .into(holder.binding.imageViewPicture)
    }

    override fun getItemCount(): Int {
        return postArrayList.size
    }

}