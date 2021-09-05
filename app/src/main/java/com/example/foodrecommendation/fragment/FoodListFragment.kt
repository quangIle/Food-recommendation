package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecommendation.adapter.HorizontalFoodListAdapter
import com.example.foodrecommendation.adapter.LoadingAdapter
import com.example.foodrecommendation.databinding.FragmentFoodListBinding
import com.example.foodrecommendation.model.FoodViewModel

class FoodListFragment : Fragment() {
    private lateinit var binding: FragmentFoodListBinding
    private val foodViewModel by activityViewModels<FoodViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodListBinding.inflate(layoutInflater, container, false)
        binding.horizontalFoodList.layoutManager = LinearLayoutManager(requireContext(),
        LinearLayoutManager.HORIZONTAL, false)

        observeFoodListState()
        return binding.root
    }

    private fun observeFoodListState() {
        foodViewModel.foodListState.observe(viewLifecycleOwner, {foodListState ->
            when (foodListState) {
                foodViewModel.COMPLETED -> {
                    binding.horizontalFoodList.adapter =
                        HorizontalFoodListAdapter(requireContext(), foodViewModel, findNavController())
                }
                foodViewModel.LOADING -> {
                    foodViewModel.loadFoodList()
                    binding.horizontalFoodList.adapter = LoadingAdapter(requireContext())
                }
                else -> {}
            }
        })
    }
}