package com.example.foodrecommendation.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue


class Food(val info: DataSnapshot)
{
    val id = info.child("ID").getValue<Long>()
    val name = info.child("Name").getValue<String>()
    val imageUrl = info.child("ImageUrl").getValue<String>()
    val youTubeUrl = info.child("YoutubeUrl").getValue<String>()
    init {
        Log.d("Fo name", info.child("ImageUrl").getValue<String>().toString())
    }
}