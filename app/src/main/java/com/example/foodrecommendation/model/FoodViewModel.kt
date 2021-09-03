package com.example.foodrecommendation.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FoodViewModel : ViewModel() {
    val COMPLETED = "Completed"
    val LOADING = "Loading"
    val TAG = "FoodViewModel"

    private val databaseUrl =
        "https://cs426-food-recommendation-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseReference = Firebase.database(databaseUrl).reference
    private val foodListRef = databaseReference.child("Food")
    private val foodMetaDataRef = databaseReference.child("Food Meta Data")

    val wheelFoodList: MutableList<Food> = ArrayList()

    init {
        Log.d(TAG, "Init ViewModel")
    }

    val foodList: MutableList<Food> = ArrayList()
    private val _foodListState = MutableLiveData(false)
    val foodListState: LiveData<String> = _foodListState.map { value ->
        if (value)
            COMPLETED
        else
            LOADING
    }

    lateinit var foodIndex: Food
    lateinit var foodMetaData: FoodMetaData
    private val _foodMetaDataState = MutableLiveData(false)
    val foodMetaDataState: LiveData<String> = _foodMetaDataState.map { value ->
        if (value)
            COMPLETED
        else
            LOADING
    }

    fun loadFoodList() {
        foodListRef.addValueEventListener(foodListRefListener)
    }

    fun loadFoodMetaData() {
        Log.d("Quang", "Load food metadata")
        foodMetaDataRef.child(foodIndex.name.toString()).addValueEventListener(foodMetaDataRefListener)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Quang", "Clear FoodViewModel")
        foodListRef.removeEventListener(foodListRefListener)
        foodMetaDataRef.child(foodIndex.name.toString()).removeEventListener(foodMetaDataRefListener)
    }

    private val foodListRefListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            Log.d(TAG, "${dataSnapshot.value}")
            for (child in dataSnapshot.children) {
                val food = Food(child)
                foodList.add(food)
            }
            _foodListState.value = true
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    }

    private val foodMetaDataRefListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            Log.d("Quang", "${dataSnapshot.value}")
            foodMetaData = FoodMetaData(dataSnapshot)
            _foodMetaDataState.value = true
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    }
}