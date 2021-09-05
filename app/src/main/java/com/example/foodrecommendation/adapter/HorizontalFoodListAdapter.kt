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
import com.airbnb.paris.extensions.style
import com.example.foodrecommendation.R
import com.example.foodrecommendation.model.FoodViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class HorizontalFoodListAdapter(
    private val context: Context,
    private val foodViewModel: FoodViewModel,
    private val navController: NavController
) : RecyclerView.Adapter<HorizontalFoodListAdapter.HorizontalFoodListViewHolder>(){
    class HorizontalFoodListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.rvItemText)
        val imageView: ImageView = view.findViewById(R.id.rvItemImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalFoodListViewHolder {
        return HorizontalFoodListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_horizontal_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HorizontalFoodListViewHolder, position: Int) {
        val item = foodViewModel.foodList[position]
        holder.textView.text = item.name
        holder.textView.style(R.style.CustomizedTextView)

        holder.imageView.style(R.style.CustomizedButton)
        foodViewModel.viewModelScope.launch {
            Picasso.get().load(item.imageUrl).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            Log.d("Dien", "item click")
            foodViewModel.foodIndex = item
            navController.navigate(R.id.foodDetailFragment)
        }
    }

    override fun getItemCount() = foodViewModel.foodList.size
}