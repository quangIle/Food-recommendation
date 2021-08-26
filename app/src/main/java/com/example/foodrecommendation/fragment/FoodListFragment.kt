package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.paris.extensions.style
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentFoodListBinding
import com.example.foodrecommendation.model.LoginViewModel

class FoodListFragment : Fragment() {
    private lateinit var binding: FragmentFoodListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodListBinding.inflate(layoutInflater, container, false)
        //Create Food List View Programmatically
        var listFood = mutableListOf<Int>(
            R.drawable.pho01,
            R.drawable.bunbohue01,
            R.drawable.banhkhot01,
            R.drawable.bundaumamtom01,
            R.drawable.comtam01
        )
        var listFoodName = mutableListOf<String>(
            "Phở", "Bún Bò Huế", "Bánh Khọt", "Bún Đậu Mắm Tôm",
            "Cơm Tấm"
        )
        for (i in 0..4) {
            var layout = LinearLayout(context)
            layout.style(R.style.LinearLayoutFoodRec)
            layout.orientation = LinearLayout.VERTICAL
            binding.layoutFoodRec.addView(layout)

            var btnFood = ImageButton(context)
            btnFood.style(R.style.CustomizedButton)
            btnFood.setImageResource(listFood[i])
            layout.addView(btnFood)

            var foodName = TextView(context)
            foodName.style(R.style.CustomizedTextView)
            foodName.text = listFoodName[i]
            layout.addView(foodName)
        }

        //Create Book Recommendation Programmatically
        var listBook = mutableListOf<Int>(
            R.drawable.book1,
            R.drawable.book2,
            R.drawable.book3,
            R.drawable.book4
        )
        var listBookName = mutableListOf<String>(
            "Vietnamese Street Food",
            "Vietnamese food any day: simple recipes for true, fresh flavors",
            "Into the Vietnamese Kitchen:  Treasured Foodways, Modern Flavors",
            "Vietnamese Home Cooking"
        )
        for (i in 0..3) {
            var layout = LinearLayout(context)
            layout.style(R.style.LinearLayoutFoodRec)
            layout.orientation = LinearLayout.VERTICAL
            binding.layoutBookRec.addView(layout)

            var btnBook = ImageButton(context)
            btnBook.style(R.style.FoodBookStyle)
            btnBook.setImageResource(listBook[i])
            layout.addView(btnBook)

            var bookName = TextView(context)
            bookName.style(R.style.CustomizedTextView)
            bookName.text = listBookName[i]
            layout.addView(bookName)
        }
        return binding.root
    }
}