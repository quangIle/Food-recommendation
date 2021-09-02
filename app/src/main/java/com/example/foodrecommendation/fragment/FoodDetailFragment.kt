package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentFoodDetailBinding
import com.example.foodrecommendation.model.Food
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.example.foodrecommendation.model.FoodMetaData

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso


class FoodDetailFragment : Fragment() {
    // viewbinding
    val TAG = "FoodDetail"
    var FOOD_ID: Long = 2
    lateinit var binding: FragmentFoodDetailBinding
    val foodList = mutableListOf<Food>()
    val foodMetaDataList = mutableListOf<FoodMetaData>()

    val databaseUrl = "https://cs426-food-recommendation-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseReference = Firebase.database(databaseUrl).reference
    private val foodRef = databaseReference.child("Food")
    private val foodMetaDataRef = databaseReference.child("Food Meta Data")

    private val foodRefListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            //Log.d(TAG, "${dataSnapshot.value}")
            for (child in dataSnapshot.children) {
                val food = Food(child)
                foodList.add(food)
                //Log.d(TAG,"${foodList}")
            }
            //Log.d(TAG,"${foodList}")
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    }

    private val foodMetaDataRefListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            //Log.d(TAG, "${dataSnapshot.value}")
            for (child in dataSnapshot.children) {
                val item = FoodMetaData(child)
                foodMetaDataList.add(item)
                //Log.d(TAG,"${ingredientList}")
            }
            //Log.d(TAG,"${foodList}")
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // apply viewbinding
        binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
        // code An
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodRef.addValueEventListener(foodRefListener)
        foodMetaDataRef.addValueEventListener(foodMetaDataRefListener)
        val handler = Handler()
        handler.postDelayed({ // Do something after 1s = 1000ms
                val food = getFood(FOOD_ID,foodList)
                val ingredient = getFoodMetaData(FOOD_ID, foodMetaDataList)
                if (food != null && ingredient != null){
                    initView(food, ingredient)
            }
        }, 1000)
    }

    fun initView(food: Food, foodMetaData: FoodMetaData){
        //Set Food Name Text View
        binding.tvFoodName.text = food.name.toString()
        //Set Origin Text View
        binding.tvOrigin.text = food.origin.toString()
        //Set Youtube PLayer
        getLifecycle().addObserver(binding.youtubeVideo);
        binding.youtubeVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = food.youTubeUrl.toString()
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
        //Set Ingredient Text View
        val ingredients = foodMetaData.ingredients
        binding.tvIngredient.text = ingredients.toString().replace(".","\n")
        //Set Steps Text View
        val steps = foodMetaData.steps
        binding.tvCookSteps.text = steps.toString().replace(".","\n")
        //Set Image View
        Picasso.get().load(food.imageUrl).into(binding.ivFoodImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        foodRef.removeEventListener(foodRefListener)
        foodMetaDataRef.removeEventListener(foodMetaDataRefListener)
    }
}

fun getFood(id: Long, foodList: MutableList<Food>): Food? {
    for (food in foodList){
        if (food.id == id) {
            return food
        }
    }
    return null
}

fun getFoodMetaData(id: Long, list: MutableList<FoodMetaData>) : FoodMetaData? {
    for (ingredient in list){
        if (ingredient.id == id) {
            return ingredient
        }
    }
    return null
}
