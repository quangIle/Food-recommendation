package com.example.foodrecommendation.utils

import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.foodrecommendation.databinding.FragmentFoodMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.android.PolyUtil
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode

class getDirection(var mMap: GoogleMap, var apiKey: String, var activity: FragmentActivity, var binding: FragmentFoodMapBinding) {
    private fun getGeoContext() : GeoApiContext {
        val geoApiContext = GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()
        return geoApiContext
    }
    fun createDirectionRequest(start: LatLng, stop: LatLng){
        //binding.btnDirection.visibility = View.GONE
        //binding.btbBack.visibility = View.GONE
        binding.storeDetail.visibility = View.GONE

        val apiRequest = DirectionsApi.newRequest(getGeoContext())
        apiRequest.origin(com.google.maps.model.LatLng(start.latitude, start.longitude))
        apiRequest.destination(com.google.maps.model.LatLng(stop.latitude, stop.longitude))
        apiRequest.mode(TravelMode.DRIVING)
        var polyline: Polyline? = null
        apiRequest.setCallback(object : com.google.maps.PendingResult.Callback<DirectionsResult> {
            override fun onResult(result: DirectionsResult?) {
                if (result != null) {
                    activity?.runOnUiThread(java.lang.Runnable {
                        addPolylineMarker(result)
                    })
                }
            }
            override fun onFailure(e: Throwable?) {
                if (e != null) {
                    Log.d("Dien Dien: ", e.message.toString())
                }
            }
        })
    }

    private fun addPolylineMarker(result: DirectionsResult) {
        val decodedPath: List<LatLng> = PolyUtil
            .decode(result.routes[0].overviewPolyline.encodedPath)
        var polyline = mMap.addPolyline(PolylineOptions().addAll(decodedPath))
        val start = LatLng(result.routes[0].legs[0].startLocation.lat,
            result.routes[0].legs[0].startLocation.lng)
        val marker = mMap.addMarker(
            MarkerOptions()
                .position(start)
                .title(result.routes[0].legs[0].startAddress))
        mMap.animateCamera(CameraUpdateFactory
            .newLatLngZoom(LatLng(start.latitude, start.longitude), 18f))
        binding.btnStop.visibility = View.VISIBLE
        binding.btnStop.setOnClickListener {
            polyline.remove()
            marker.remove()
            binding.storeDetail.visibility = View.VISIBLE
            binding.btnExit.visibility = View.VISIBLE
            binding.btnStop.visibility = View.GONE
        }
    }
}