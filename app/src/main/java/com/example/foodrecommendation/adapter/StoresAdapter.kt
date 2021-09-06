package com.example.foodrecommendation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentFoodMapBinding
import com.example.foodrecommendation.model.Store
import com.example.foodrecommendation.utils.getDirection
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class StoresAdapter(
    private val mMap: GoogleMap,
    private val binding: FragmentFoodMapBinding,
    private val storeList: ArrayList<Store>,
    private val activity: FragmentActivity,
    private val currentLocation: LatLng,
    private val apiKey: String
) : RecyclerView.Adapter<StoresAdapter.StoreViewHolder>() {

    class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_name)
        val vicinityTextView: TextView = view.findViewById(R.id.item_vicinity)
        val openingTextView: TextView = view.findViewById(R.id.item_opening)
        val ratingTextView: TextView = view.findViewById(R.id.item_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.store_item, parent,false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storeList[position]
        holder.nameTextView.text = store.name
        holder.vicinityTextView.text = store.vicinity
        var status: String
        var rate: String
        if (store.opening.contains("true")){
            status = "Open"
        } else {
            status = "Closed"
        }
        rate = "Rating: " + store.rating + "/5" + "  (" + store.numOfVoter + " voters)"
        holder.openingTextView.text = status
        holder.ratingTextView.text = rate

        holder.itemView.setOnClickListener {
            Log.d("Dien", "item click")
            binding.recyclerView.visibility = View.GONE
            binding.storeDetail.visibility = View.VISIBLE
            binding.storeDetailName.text =  store.name
            binding.storeDetailLoca.text = store.vicinity
            binding.storeDetailRating.text = rate
            binding.storeDetailOpeninghour.text = status

            mMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(LatLng(store.latlng.lat, store.latlng.lng), 16f))

            binding.btnDirection.setOnClickListener {
                val getDirection = getDirection(mMap, apiKey, activity, binding)
                getDirection.createDirectionRequest(currentLocation, LatLng(store.latlng.lat, store.latlng.lng))
            }

            binding.btbBack.setOnClickListener {
                binding.recyclerView.visibility = View.VISIBLE
                binding.storeDetail.visibility = View.GONE
                mMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(LatLng(store.latlng.lat, store.latlng.lng), 13f))
            }
        }
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}