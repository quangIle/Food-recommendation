package com.example.foodrecommendation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecommendation.R
import com.example.foodrecommendation.model.FoodViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class VerticalListAdapter(
    private val context: Context,
    private val foodViewModel: FoodViewModel,
    private val navController: NavController
) : RecyclerView.Adapter<VerticalListAdapter.FoodVerticalListViewHolder>() {
    class FoodVerticalListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.rvItemText)
        val imageView: ImageView = view.findViewById(R.id.rvItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodVerticalListViewHolder {
        return FoodVerticalListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FoodVerticalListViewHolder, position: Int) {
        val item = foodViewModel.foodList[position]
        holder.textView.text = item.name

        foodViewModel.viewModelScope.launch {
            Picasso.get().load(item.imageUrl).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            Log.d("Quang", "item click")
            foodViewModel.foodIndex = item
            navController.navigate(R.id.foodDetailFragment)
        }
    }

    override fun getItemCount() = foodViewModel.foodList.size
}