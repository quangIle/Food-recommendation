package com.example.foodrecommendation.fragment

import android.content.Context
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
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask
import kotlin.math.log
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import androidx.annotation.NonNull

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso


class FoodDetailFragment : Fragment() {
    // viewbinding
    val TAG = "FoodDetail"
    lateinit var binding: FragmentFoodDetailBinding
    val foodList = mutableListOf<Food>()

    val databaseUrl = "https://cs426-food-recommendation-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseReference = Firebase.database(databaseUrl).reference
    private val foodRef = databaseReference.child("Food")

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
        val handler = Handler()
        handler.postDelayed({ // Do something after 1s = 1000ms
            val food = getFood(3,foodList)
            if (food != null){
                initView(food)
            }
        }, 1200)
    }


    /*override fun onStart() {
        /*super.onStart()
        Log.d(TAG,"${foodList}")
        val food = getFood(1,foodList)
        /*if (food!=null){
            Log.d(TAG,"${food.id} ${food.imageUrl} ${food.name} ${food.youTubeUrl}")
        }*/
        if (food != null) {
            binding.tvFoodName.text = food.name.toString()
        }*/
        /*binding.tvIngredient.text = displayItems(ingredientsList)
        binding.tvCookSteps.text = displayItems(list)
        var count = 0
        binding.tvFoodImage.setOnClickListener {
            count = (count + 1) % 3
            binding.tvFoodImage.setImageResource(getResourceId("spaghetti", count, requireContext()))
        }*/
    }*/

    fun initView(food: Food){
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
        //Set Image View
        Picasso.get().load(food.imageUrl).into(binding.ivFoodImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        foodRef.removeEventListener(foodRefListener)
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

fun displayItems(list: MutableList<String>): String {
    var items = ""
    for (i in list.indices) {
        items = items + (i + 1).toString() + ". " + list[i] + "\n"
    }
    return items
}

fun getResourceId(filename: String, count: Int, context: Context): Int {
    val id: Int = context.resources.getIdentifier(
        filename + "_" + count.toString(),
        "drawable",
        context.packageName
    )
    return id
}
