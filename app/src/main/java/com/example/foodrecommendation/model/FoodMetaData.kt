package com.example.foodrecommendation.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue


class FoodMetaData(val info: DataSnapshot)
{
    val id = info.child("ID").getValue<Long>()
    val ingredients = info.child("Ingredients").getValue<String>()
    val steps = info.child("Steps").getValue<String>()
}