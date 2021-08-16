package com.example.foodrecommendation.fragment

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentFoodListBinding
import android.util.Log.d as d1

class FoodListFragment : Fragment() {
    private lateinit var binding: FragmentFoodListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate<FragmentFoodListBinding>(inflater,
        R.layout.fragment_food_list,container,false)
        binding.pho1.setOnClickListener {
            Log.d("Haha", "Dien");
            Toast.makeText(activity, "Hello world", Toast.LENGTH_SHORT).show();
        }

        return binding.root
    }




}