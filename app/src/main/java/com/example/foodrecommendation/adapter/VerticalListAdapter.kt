package com.example.foodrecommendation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.paris.utils.setPaddingBottom
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.RecyclerViewItemBinding
import com.example.foodrecommendation.model.FoodViewModel
import com.example.foodrecommendation.model.HistoryViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class VerticalListAdapter(
    private val context: Context,
    private val foodViewModel: FoodViewModel,
    val historyViewModel: HistoryViewModel,
    private val navController: NavController
) : RecyclerView.Adapter<VerticalListAdapter.FoodVerticalListViewHolder>() {

    class FoodVerticalListViewHolder(binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val textView: TextView = binding.rvItemText
        val imageView: ImageView = binding.rvItemImage
        val itemOrigin: TextView = binding.rvItemOrigin
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodVerticalListViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = RecyclerViewItemBinding.inflate(inflater, parent, false)
        return FoodVerticalListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodVerticalListViewHolder, position: Int) {
        val item = foodViewModel.foodList[position]
        holder.textView.text = item.name
        holder.itemOrigin.text = item.origin

        foodViewModel.viewModelScope.launch {
            Picasso.get().load(item.imageUrl).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            historyViewModel.viewModelScope.launch {
                historyViewModel.writeUserHistory(item.name.toString())
            }
            foodViewModel.foodIndex = item
            navController.navigate(R.id.foodDetailFragment)
        }
        if (position == itemCount - 1){
            holder.root.setPaddingBottom(80)
        }
        else holder.root.setPaddingBottom(0)
    }

    override fun getItemCount() = foodViewModel.foodList.size
}