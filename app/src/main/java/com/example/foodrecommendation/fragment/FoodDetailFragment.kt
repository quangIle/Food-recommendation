package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentFoodDetailBinding
import com.example.foodrecommendation.model.FoodViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso


class FoodDetailFragment : Fragment() {
    private lateinit var binding: FragmentFoodDetailBinding
    private val foodViewModel by activityViewModels<FoodViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        observeFoodMetaDataState()
        this.requireActivity()
            .findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .visibility = View.INVISIBLE

        return binding.root
    }

    private fun observeFoodMetaDataState() {
        foodViewModel.foodMetaDataState.observe(viewLifecycleOwner, { foodMetaDataState ->
            when (foodMetaDataState) {
                foodViewModel.COMPLETED -> {
                    loadMetaDataToView()
                }
                foodViewModel.LOADING -> {
                    foodViewModel.loadFoodMetaData()
                }
                else -> {
                }
            }
        })
    }

    private fun loadMetaDataToView() {
        val foodIndex = foodViewModel.foodIndex
        val foodMetaData = foodViewModel.foodMetaData
        //Set Food Name Text View
        binding.tvFoodName.text = foodIndex.name.toString()
        //Set Origin Text View
        binding.tvOrigin.text = foodIndex.origin.toString()
        //Set Youtube PLayer
        lifecycle.addObserver(binding.youtubeVideo)
        binding.youtubeVideo.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = foodIndex.youTubeUrl.toString()
                //youTubePlayer.loadVideo(videoId, 0f)
                val START_TIME: Float = 0.toFloat()
                youTubePlayer.cueVideo(videoId, START_TIME)
            }
        })
        //Set Ingredient Text View
        val ingredients = foodMetaData.ingredients
        binding.tvIngredient.expandTextView.text = ingredients.toString().replace(".", "\n")
        //Set Steps Text View
        val steps = foodMetaData.steps
        binding.tvCookSteps.expandTextView.text = steps.toString().replace(".", "\n")
        //Set Image View
        Picasso.get().load(foodIndex.imageUrl).into(binding.ivFoodImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.requireActivity()
            .findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .visibility = View.VISIBLE
    }
}
