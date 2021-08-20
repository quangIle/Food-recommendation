package com.example.foodrecommendation.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FoodViewModel : ViewModel() {
    val TAG = "FoodViewModel"
    val foodList: MutableList<Food> = ArrayList<Food>()
    val wheelFoodList: MutableList<Food> = ArrayList<Food>()
    val databaseUrl = "https://cs426-food-recommendation-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseReference = Firebase.database(databaseUrl).reference
    private val foodRef = databaseReference.child("Food")

    private val foodRefListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            Log.d(TAG, "${dataSnapshot.value}")
            for (child in dataSnapshot.children) {
                // TODO remove this
                if (dataSnapshot.children.count() == 1)
                {
                    foodList.add(Food(child))
                    foodList.add(Food(child))
                    foodList.add(Food(child))
                }
                val food = Food(child)
                foodList.add(food)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    }

    init {
        Log.d(TAG, "Init ViewModel " + foodRef.toString())
        foodRef.addValueEventListener(foodRefListener)
    }

    fun loadFoodList() {
        //foodList = listOf<String>("abc", "xyz")
    }

    fun addFoodToWheel(food: Food) {
        wheelFoodList.add(food)
    }

    override fun onCleared() {
        super.onCleared()
        foodRef.removeEventListener(foodRefListener)
    }
}