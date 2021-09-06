package com.example.foodrecommendation.utils

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecommendation.adapter.StoresAdapter
import com.example.foodrecommendation.databinding.FragmentFoodMapBinding
import com.example.foodrecommendation.model.Store
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.model.LatLng
import java.io.IOException

class GetNearbyPlacesData(var binding: FragmentFoodMapBinding, var activity: FragmentActivity)
    : AsyncTask<Any?, String?, String?>() {
    private var googlePlacesData: String? = null
    private var mMap: GoogleMap? = null
    private var currentLocation: com.google.android.gms.maps.model.LatLng? = null
    private var apiKey: String? = null
    private var url: String? = null
    // var foodMapFragment: FoodMapFragment? = null
    @SuppressLint("StaticFieldLeak")
    var recyclerView: RecyclerView? = null
    var storesList: ArrayList<Store> = ArrayList()


    override fun doInBackground(vararg objects: Any?): String? {
        mMap = objects[0] as GoogleMap
        url = objects[1] as String
        currentLocation = objects[2] as com.google.android.gms.maps.model.LatLng
        apiKey = objects[3] as String
        val downloadURL = DownloadURL()
        try {
            googlePlacesData = downloadURL.readUrl(url)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return googlePlacesData
    }

    override fun onPostExecute(result: String?) {
        val nearbyPlaceList: List<HashMap<String, String>>
        val parser = DataParser()
        nearbyPlaceList = parser.parse(result) as List<HashMap<String, String>>
        Log.d("nearbyplacesdata", "called parse method")
        showNearbyPlaces(nearbyPlaceList)
    }


    private fun showNearbyPlaces(nearbyPlaceList: List<HashMap<String, String>>) {
        for (i in nearbyPlaceList.indices) {
            val markerOptions = MarkerOptions()
            val googlePlace = nearbyPlaceList[i]

            val placeName = googlePlace["place_name"]
            val vicinity = googlePlace["vicinity"]
            val rating = googlePlace["rating"]
            val numVoter = googlePlace["numVoter"]
            val opening = googlePlace["opening"]

            if (googlePlace["lat"].isNullOrEmpty() || googlePlace["lng"].isNullOrEmpty())
                continue
            val lat = googlePlace["lat"]!!.toDouble()
            val lng = googlePlace["lng"]!!.toDouble()
            val latLng = com.google.android.gms.maps.model.LatLng(lat, lng)
            markerOptions.position(latLng)
            markerOptions.title("$placeName : $vicinity")
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            mMap!!.addMarker(markerOptions)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(13f))
            val store = Store(
                i+1,
                placeName.toString(),
                vicinity.toString(),
                opening.toString(),
                rating.toString(),
                numVoter.toString(),
                LatLng(lat,lng)
            )
            storesList?.add(store)
        }
        binding.recyclerView.visibility = View.VISIBLE
        binding.btnExit.visibility = View.VISIBLE
        recyclerView = binding.recyclerView
        val adapter = StoresAdapter(mMap!!, binding, storesList!!, activity, currentLocation!!,
            apiKey!!
        )
        recyclerView?.adapter = adapter
    }
}