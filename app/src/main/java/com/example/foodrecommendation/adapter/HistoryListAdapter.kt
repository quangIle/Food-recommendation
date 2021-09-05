package com.example.foodrecommendation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecommendation.R

class HistoryListAdapter(private val nameSet: Set<String?>) :
    RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder>() {
    private val nameList = nameSet.toList()

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.history_food_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.textView.text = nameList[position]
    }

    override fun getItemCount() = nameList.size
}