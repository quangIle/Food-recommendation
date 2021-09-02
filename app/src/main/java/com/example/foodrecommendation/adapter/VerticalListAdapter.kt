package com.example.foodrecommendation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecommendation.R
import com.example.foodrecommendation.model.FoodViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class VerticalListAdapter(private val context: Context, private val viewModel: FoodViewModel) : RecyclerView.Adapter<VerticalListAdapter.FoodVerticalListViewHolder>() {
    class FoodVerticalListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.rvItemText)
        val imageView: ImageView = view.findViewById(R.id.rvItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodVerticalListViewHolder {
        return FoodVerticalListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: FoodVerticalListViewHolder, position: Int) {
        val item = viewModel.foodList[position]
        holder.textView.text = item.name

        val gsReference = Firebase.storage.getReferenceFromUrl(item.imageUrl!!)
        Glide.with(context).load(gsReference).into(holder.imageView)

        holder.itemView.setOnClickListener {
            Log.d("Quang", "item click")
            viewModel.foodDetail = item
        }
    }

    override fun getItemCount() = viewModel.foodList.size
}