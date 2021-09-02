package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecommendation.adapter.LoadingAdapter
import com.example.foodrecommendation.adapter.VerticalListAdapter
import com.example.foodrecommendation.databinding.FragmentFoodVerticalListBinding
import com.example.foodrecommendation.model.FoodViewModel

class VerticalListFragment : Fragment() {
    private lateinit var binding: FragmentFoodVerticalListBinding
    private val foodViewModel by activityViewModels<FoodViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodVerticalListBinding.inflate(layoutInflater, container, false)
        binding.verticalFoodList.layoutManager = LinearLayoutManager(requireContext())
        Log.d("Quang", "create list fragment")

        observeFoodListState()

        return binding.root
    }

    private fun observeFoodListState() {
        foodViewModel.foodListState.observe(viewLifecycleOwner, { foodListState ->
            val completed = "Completed"
            val loading = "Loading"
            when (foodListState) {
                completed -> {
                    binding.verticalFoodList.adapter =
                        VerticalListAdapter(requireContext(), foodViewModel)
                }
                loading -> {
                    foodViewModel.loadFoodList()
                    binding.verticalFoodList.adapter = LoadingAdapter(requireContext())
                }
                else -> {
                }
            }

        })
    }
}