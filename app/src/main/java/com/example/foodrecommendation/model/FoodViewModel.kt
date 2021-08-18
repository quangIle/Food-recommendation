package com.example.foodrecommendation.model

import androidx.lifecycle.ViewModel

class FoodViewModel : ViewModel() {
    lateinit var foodList: List<Food>
    val wheelFoodList: MutableList<Food> = ArrayList<Food>()

    init {

    }

    fun loadFoodList() {
        //foodList = listOf<String>("abc", "xyz")
    }

    fun addFoodToWheel(food: Food) {
        wheelFoodList.add(food)
    }
}