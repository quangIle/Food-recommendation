package com.example.foodrecommendation.model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue


data class Food(val info: DataSnapshot)
{
    val id = info.child("id").getValue<String>()
    val name = info.child("name").getValue<String>()
    val imageUrl = info.child("ImageUrl").getValue<String>()
    val youTubeUrl = info.child("YoutubeUrl").getValue<String>()
}