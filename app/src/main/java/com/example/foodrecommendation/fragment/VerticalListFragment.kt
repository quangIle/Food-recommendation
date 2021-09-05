package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecommendation.adapter.LoadingAdapter
import com.example.foodrecommendation.adapter.VerticalListAdapter
import com.example.foodrecommendation.databinding.FragmentFoodVerticalListBinding
import com.example.foodrecommendation.model.FoodViewModel
import com.example.foodrecommendation.model.HistoryViewModel

class VerticalListFragment : Fragment() {
    private lateinit var binding: FragmentFoodVerticalListBinding
    private val foodViewModel by activityViewModels<FoodViewModel>()
    private val historyViewModel by activityViewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodVerticalListBinding.inflate(layoutInflater, container, false)
        binding.verticalFoodList.layoutManager = LinearLayoutManager(requireContext())

        observeFoodListState()

        return binding.root
    }

    private fun observeFoodListState() {
        foodViewModel.foodListState.observe(viewLifecycleOwner, { foodListState ->
            when (foodListState) {
                foodViewModel.COMPLETED -> {
                    binding.verticalFoodList.adapter =
                        VerticalListAdapter(requireContext(), foodViewModel, historyViewModel, findNavController())
                }
                foodViewModel.LOADING -> {
                    foodViewModel.loadFoodList()
                    binding.verticalFoodList.adapter = LoadingAdapter(requireContext())
                }
                else -> {
                }
            }
        })
    }
}