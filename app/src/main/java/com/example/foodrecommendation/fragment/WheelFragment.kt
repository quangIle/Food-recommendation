package com.example.foodrecommendation.fragment

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluehomestudio.luckywheel.WheelItem
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentWheelBinding

/**
 * A simple [Fragment] subclass.
 * Use the [WheelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WheelFragment : Fragment(R.layout.fragment_wheel) {

    private var _binding: FragmentWheelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWheelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareData()
    }

    private fun prepareData() {
        val wheelItems: MutableList<WheelItem> = ArrayList()
        wheelItems.add(
            WheelItem(
                Color.WHITE,
                BitmapFactory.decodeResource(resources, R.drawable.ic_action_name),
                "Com Tam"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.WHITE,
                BitmapFactory.decodeResource(resources, R.drawable.ic_action_name),
                "Bun bo"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.WHITE,
                BitmapFactory.decodeResource(resources, R.drawable.ic_action_name),
                "Pho"
            )
        )
        binding.mainWheel.addWheelItems(wheelItems)
    }
}
