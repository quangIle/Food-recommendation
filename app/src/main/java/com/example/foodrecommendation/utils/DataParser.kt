package com.example.foodrecommendation.utils

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class DataParser {
    private fun getPlace(googlePlaceJson: JSONObject): HashMap<String, String> {
        val googlePlaceMap = HashMap<String, String>()
        var placeName = "--NA--"
        var vicinity = "--NA--"
        var opening = ""
        var numVoter = "--NA--"
        var rating = "--NA--"
        var latitude = ""
        var longitude = ""
        Log.d("DataParser", "jsonobject =$googlePlaceJson")
        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name")
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity")
            }
            if (!googlePlaceJson.isNull("rating")){
                rating = googlePlaceJson.getString("rating")
            }
            if (!googlePlaceJson.isNull("user_ratings_total")){
                numVoter = googlePlaceJson.getString("user_ratings_total")
            }
            opening =
                googlePlaceJson.getJSONObject("opening_hours").getString("open_now")
            latitude =
                googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat")
            longitude =
                googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng")
            googlePlaceMap.put("place_name",placeName)
            googlePlaceMap.put("vicinity", vicinity)
            googlePlaceMap.put("rating", rating)
            googlePlaceMap.put("numVoter", numVoter)
            googlePlaceMap.put("opening", opening)
            googlePlaceMap.put("lat", latitude)
            googlePlaceMap.put("lng", longitude)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return googlePlaceMap
    }

    private fun getPlaces(jsonArray: JSONArray?): List<HashMap<String, String>?> {
        val count = jsonArray!!.length()
        val placelist: MutableList<HashMap<String, String>?> = ArrayList()
        var placeMap: HashMap<String, String>? = null
        for (i in 0 until count) {
            try {
                placeMap = getPlace(jsonArray[i] as JSONObject)
                placelist.add(placeMap)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return placelist
    }

    fun parse(jsonData: String?): List<HashMap<String, String>?> {
        var jsonArray: JSONArray? = null
        val jsonObject: JSONObject
        Log.d("json data", jsonData.toString())
        try {
            jsonObject = JSONObject(jsonData)
            jsonArray = jsonObject.getJSONArray("results")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return getPlaces(jsonArray)
    }
}