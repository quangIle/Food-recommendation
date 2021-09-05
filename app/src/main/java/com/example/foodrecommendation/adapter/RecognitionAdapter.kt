package com.example.foodrecommendation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.RecognitionItemBinding
import com.example.foodrecommendation.model.FoodViewModel
import com.example.foodrecommendation.model.HistoryViewModel
import com.example.foodrecommendation.model.Recognition

class RecognitionAdapter(
    private val ctx: Context,
    private val foodViewModel: FoodViewModel,
    private val historyViewModel: HistoryViewModel,
    private var navController: NavController
) :
    ListAdapter<Recognition, RecognitionViewHolder>(RecognitionDiffUtil()) {

    /**
     * Inflating the ViewHolder with recognition_item layout and data binding
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecognitionViewHolder {
        val inflater = LayoutInflater.from(ctx)
        val binding = RecognitionItemBinding.inflate(inflater, parent, false)
        return RecognitionViewHolder(binding)
    }

    // Binding the data fields to the RecognitionViewHolder
    override fun onBindViewHolder(holder: RecognitionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
        holder.binding.foodChip.setOnClickListener {
            foodViewModel.foodIndex = foodViewModel.foodList.find {
                it.name == item.name
            }
            historyViewModel.writeUserHistory(item.name)
            navController.navigate(R.id.foodDetailFragment)
        }
    }

    private class RecognitionDiffUtil : DiffUtil.ItemCallback<Recognition>() {
        override fun areItemsTheSame(oldItem: Recognition, newItem: Recognition): Boolean {
            return oldItem.label == newItem.label
        }

        override fun areContentsTheSame(oldItem: Recognition, newItem: Recognition): Boolean {
            return oldItem.confidence == newItem.confidence
        }
    }


}

class RecognitionViewHolder(val binding: RecognitionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    // Binding all the fields to the view - to see which UI element is bind to which field, check
    // out layout/recognition_item.xml
    fun bindTo(recognition: Recognition) {
        binding.recognitionItem = recognition
        binding.executePendingBindings()
    }
}