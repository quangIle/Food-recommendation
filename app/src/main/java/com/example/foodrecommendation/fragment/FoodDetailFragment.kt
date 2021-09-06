package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentFoodDetailBinding
import com.example.foodrecommendation.model.BrowserViewModel
import com.example.foodrecommendation.model.FoodViewModel
import com.example.foodrecommendation.model.MapViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import np.com.susanthapa.curved_bottom_navigation.CurvedBottomNavigationView


class FoodDetailFragment : Fragment() {
    private lateinit var binding: FragmentFoodDetailBinding
    private val foodViewModel by activityViewModels<FoodViewModel>()
    private val browserViewModel by viewModels<BrowserViewModel>()
    private val mapViewModel by activityViewModels<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //browserViewModel.getReady(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        observeFoodMetaDataState()
        this.requireActivity()
            .findViewById<CurvedBottomNavigationView>(R.id.bottom_navigation)
            .visibility = View.INVISIBLE

        foodViewModel.loadFoodMetaData()

        mapViewModel.foodName = foodViewModel.foodIndex?.name.toString()

        binding.mapButton.setOnClickListener {
            //browserViewModel.launchBrowser(foodViewModel.foodIndex?.name.toString())
            findNavController().navigate(R.id.action_foodDetailFragment_to_foodMapFragment)
        }
        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun observeFoodMetaDataState() {
        foodViewModel.foodMetaDataState.observe(viewLifecycleOwner, { foodMetaDataState ->
            when (foodMetaDataState) {
                foodViewModel.COMPLETED -> {
                    loadMetaDataToView()
                }
                else -> {
                }
            }
        })
    }

    private fun loadMetaDataToView() {
        val foodIndex = foodViewModel.foodIndex!!
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

    override fun onDetach() {
        super.onDetach()
        this.requireActivity()
            .findViewById<CurvedBottomNavigationView>(R.id.bottom_navigation)
            .visibility = View.VISIBLE
    }
}
