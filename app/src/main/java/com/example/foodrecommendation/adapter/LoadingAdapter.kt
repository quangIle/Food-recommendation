package com.example.foodrecommendation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecommendation.R

class LoadingAdapter(private val context: Context) :
    RecyclerView.Adapter<LoadingAdapter.LoadingViewHolder>() {
    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.loadingImageForList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        return LoadingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.loading, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {
        Glide.with(context).load(R.drawable.loading).into(holder.imageView)

    }

    override fun getItemCount() = 1
}