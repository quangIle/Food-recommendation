package com.example.foodrecommendation.model

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.foodrecommendation.R
import com.example.foodrecommendation.fragment.FoodDetailFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class BrowserViewModel() : ViewModel() {
    private lateinit var context: Fragment
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var food: String
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    init {
        Log.d("Quang", "Init BrowserViewModel")
    }

    fun launchBrowser(foodName: String) {
        food = foodName

        if (ContextCompat.checkSelfPermission(
                context.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context.requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                val lat = location?.latitude ?: 10.7367051
                val lon = location?.longitude ?: 106.6645009
                val keyword = food.replace(' ', '+')
                startCustomTabs(keyword, lat, lon)
            }
        } else requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun startCustomTabs(keyword: String, lat: Double, lon: Double) {
        val url = "https://www.google.com/maps/search/${keyword}/@${lat},${lon},15z"

        val customTabsBuilder = CustomTabsIntent.Builder()
        customTabsBuilder.setStartAnimations(
            context.requireContext(),
            R.anim.fui_slide_in_right,
            R.anim.fui_slide_out_left
        )
        customTabsBuilder.setExitAnimations(
            context.requireContext(),
            R.anim.fui_slide_out_left,
            R.anim.fui_slide_in_right
        )

        val customTabsIntent = customTabsBuilder.build()
        customTabsIntent.launchUrl(context.requireContext(), Uri.parse(url))
    }

    fun getReady(foodDetailFragment: FoodDetailFragment) {
        Log.d("Quang", "BrowserViewModel is ready")
        context = foodDetailFragment
        requestPermissionLauncher =
            context.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(
                        context.requireContext(),
                        "Permission granted!",
                        Toast.LENGTH_LONG
                    ).show()
                    launchBrowser(food)
                }
            }
    }
}