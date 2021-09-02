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
    val TAG = "FoodViewModel"
    val databaseUrl =
        "https://cs426-food-recommendation-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseReference = Firebase.database(databaseUrl).reference
    private val foodRef = databaseReference.child("Food")

    val foodList: MutableList<Food> = ArrayList<Food>()
    lateinit var foodDetail: Food
    val wheelFoodList: MutableList<Food> = ArrayList<Food>()

    init {
        Log.d(TAG, "Init ViewModel")
    }

    private val _foodListState = MutableLiveData<Boolean>(false)
    val foodListState: LiveData<String> = _foodListState.map { value ->
        val completed = "Completed"
        val loading = "Loading"
        if (value)
            completed
        else
            loading
    }

    fun loadFoodList() {
        foodRef.addValueEventListener(foodRefListener)
    }

    fun addFoodToWheel(food: Food) {
        wheelFoodList.add(food)
    }

    override fun onCleared() {
        super.onCleared()
        foodRef.removeEventListener(foodRefListener)
    }

    private val foodRefListener = object : ValueEventListener {
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
}