package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.verticalFoodList.adapter =
            VerticalListAdapter(requireContext(), foodViewModel.foodList)
        binding.verticalFoodList.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.verticalFoodList.adapter =
            VerticalListAdapter(requireContext(), foodViewModel.foodList)
    }
}