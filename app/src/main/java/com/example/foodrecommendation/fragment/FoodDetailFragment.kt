package com.example.foodrecommendation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentFoodDetailBinding

class FoodDetailFragment : Fragment() {
    // viewbinding
    lateinit var binding: FragmentFoodDetailBinding

    val list = mutableListOf<String>(
        "We firstly need 400g of tomatoes. Use a knife and lightly cut an X on top of the tomatoes",
        "Then put them all in a boiling pot of water. Leave it to cook for 30 seconds then take it out",
        "Next, carve out the seeds of the tomato",
        "After that, place your tomatoes neatly into the blender and dismember them. You can also mince them instead.",
        "After that, set it aside",
        "Next I'll cut up some onions and cry and then blame it on the onions",
        "Put all the onion into a bowl",
        "Next, cut 20g of bell pepper. Bell peppers aren't spicy so don't worry if you can't eat spicy food. You can replace it with carrots or just skip this ingredient",
        "Now that you're done with the ingredients prepping phase, you can start cooking. I'll start with 20g of butter in a pan. You can also use olive oil",
        "I'll throw in 10g of minced garlic into the pan after the butter is melted",
        "Add in your chopped bell pepper and onion. Mix it repeatedly until all of it starts to turn golden and smells super good",
        "Then add 150g of minced beef into the pan. Pan-fry it for about 2-4 minutes",
        "Next, pour in all the blended tomatoes from earlier into the pan",
        "And add in 50ml of water into the pan as well",
        "Mix it well then add in all the spices: ―tsp of salt and 2 tsp of sugar. You can change this to taste and the sourness of the tomatoes themselves",
        "Next is 50g of ketchup (not tomato sauce, don't mix them up and 1 tsp of dried oregano",
        "Lastly is a bit of grounded pepper and some chopped culantro (not cilantro)",
        "Mix it well then close the lid and turn down the heat to low and wait for about 20min, until the sauce becomes denser",
        "You can put in more spices if you want since the sauce is basically done now. When you're done with that, pour it on your pasta.",
    )
    val ingredientsList = mutableListOf<String>(
        "400g Tomatoes",
        "40g Onion",
        "40g Bell pepper",
        "20g Unsalted butter",
        "10g Garlic",
        "150g Beef",
        "1/2Tsp Salt",
        "2Tsp Sugar",
        "50g Ketchup",
        "1Tsp Dried Oregano",
        "Black pepper",
        "10g Parsley"
    )

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

    override fun onStart() {
        super.onStart()
        binding.tvIngredient.text = displayItems(ingredientsList)
        binding.tvCookSteps.text = displayItems(list)
        var count = 0
        binding.tvFoodImage.setOnClickListener {
            count = (count + 1) % 3
            binding.tvFoodImage.setImageResource(getResourceId("spaghetti", count, requireContext()))
        }
    }
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
