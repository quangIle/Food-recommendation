package com.example.foodrecommendation.fragment

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentFoodMapBinding
import com.example.foodrecommendation.utils.GetNearbyPlacesData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException

class FoodMapFragment : Fragment(), OnMapReadyCallback{
    private lateinit var binding: FragmentFoodMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var permissionDenied: Boolean = false

    private var LOCATION_PERMISSION_REQUEST_CODE: Int = 1


    private lateinit var placesClient: PlacesClient
    private lateinit var btnFind: Button
    private lateinit var fab: FloatingActionButton
    private lateinit var keyword: EditText

    var PROXIMITY_RADIUS = 7000
    var latitude = 10.7704493
    var longitude = 106.6558233

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        Places.initialize(requireActivity(), getString(R.string.google_maps_key))
        placesClient = Places.createClient(requireActivity())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentFoodMapBinding>(inflater,
            R.layout.fragment_food_map, container, false)

        btnFind = binding.btnFind
        fab = binding.fab
        keyword = binding.inputFoodName

        //   val recyclerView: RecyclerView = binding.recyclerView

        btnFind.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val dataTransfer = arrayOfNulls<Any>(4)
                val getNearbyPlacesData = GetNearbyPlacesData(binding, activity!!)
                binding.recyclerView.visibility = View.GONE
                binding.storeDetail.visibility = View.GONE
                binding.btnExit.visibility = View.GONE
                mMap.clear()
                val restaurant = "restaurant"
                val url = getUrl(latitude, longitude, restaurant, keyword.text.toString())
                dataTransfer[0] = mMap
                dataTransfer[1] = url
                dataTransfer[2] = LatLng(latitude, longitude)
                dataTransfer[3] = getString(R.string.google_maps_key)
                getNearbyPlacesData.execute(*dataTransfer)
                Toast.makeText(requireActivity(), "Showing Nearby Restaurants", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        binding.btnExit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                binding.recyclerView.visibility = View.GONE
                binding.btnExit.visibility = View.GONE
                binding.storeDetail.visibility = View.GONE
                mMap.clear()
                showCurrentLocation()
            }
        })


        fab.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                showCurrentLocation()
                Toast.makeText(activity, "My location button clicked", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String, keyword: String): String {
        val googlePlaceUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlaceUrl.append("location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=$PROXIMITY_RADIUS")
        googlePlaceUrl.append("&type=$nearbyPlace")
        googlePlaceUrl.append("&keyword=$keyword")
        googlePlaceUrl.append("&sensor=true")
        googlePlaceUrl.append("&key=" + getString(R.string.google_maps_key))
        Log.d("MapsActivity", "url = $googlePlaceUrl")
        return googlePlaceUrl.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(object: GoogleMap.OnMapClickListener{
            override fun onMapClick(it: LatLng) {
                mMap.addMarker(MarkerOptions()
                    .position(it)
                    .title("Hehe"))
            }

        })
        mMap.setOnMarkerClickListener(object: GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(it: Marker): Boolean {
                Toast.makeText(requireActivity(), it.title, Toast.LENGTH_SHORT).show()
                return true
            }
        })
        mMap.setOnPoiClickListener(object : GoogleMap.OnPoiClickListener{
            override fun onPoiClick(it: PointOfInterest) {
                Toast.makeText(activity, """Clicked: ${it.name}
            Place ID:${it.placeId}
            Latitude:${it.latLng.latitude} Longitude:${it.latLng.longitude}""", Toast.LENGTH_SHORT).show()
            }

        })
        enableMyLocation()
        showCurrentLocation()
    }

    private fun placeMarkerOnMap(location: LatLng){
        val markerOptions = MarkerOptions().position(location)
            .icon(
                BitmapDescriptorFactory.fromBitmap(
                    Bitmap.createScaledBitmap(
                resources.getDrawable(R.drawable.hellokitty).toBitmap(), 150, 150, false)))
        markerOptions.title(getAddress(location))
        mMap.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(requireActivity())
        val addresses: List<Address>
        val address: Address
        var addressText = ""
        try{
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if(addresses != null && !addresses.isEmpty()){
                address = addresses[0]
                for(i in 0 until address.maxAddressLineIndex){
                    addressText += if(i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException){
            Log.e("FoodMapFragment", e.localizedMessage.toString())
        }
        return  addressText
    }

    private fun showCurrentLocation() {
        if(checkPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) {location ->
                if(location != null){
                    latitude = location.latitude
                    longitude = location.longitude
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    placeMarkerOnMap(currentLocation)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16f))
                }
            }
        }
    }

    private fun checkPermission(): Boolean{
        if(ActivityCompat.checkSelfPermission(requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false
        return true
    }

    private fun enableMyLocation() {
        if(ActivityCompat.checkSelfPermission(requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(mMap  != null)
                mMap.isMyLocationEnabled = true
        } else{
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode != LOCATION_PERMISSION_REQUEST_CODE)
            return
        if(checkPermission())
            enableMyLocation()
        else
            permissionDenied = true
    }
}