package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodrecommendation.R
import kotlinx.android.synthetic.main.fragment_food_detail.*

class FoodDetailFragment : Fragment() {

    val list = mutableListOf<String>("bun bo","banh canh","cha gio")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvIngredient.text = displayIngredients(list)
    }
}

fun displayIngredients(list: MutableList<String>): String {
    var ingredients = ""
    for (i in list.indices) {
        ingredients = ingredients + list[i] + '\n'
    }
    return ingredients
}