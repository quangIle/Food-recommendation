package com.example.foodrecommendation.fragment

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bluehomestudio.luckywheel.WheelItem
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentWheelBinding
import kotlin.collections.ArrayList
import kotlin.random.Random

class WheelFragment : Fragment(R.layout.fragment_wheel),
    DeleteDialogFragment.OnMultiChoiceListener {

    private var _binding: FragmentWheelBinding? = null
    private val binding get() = _binding!!
    private var wheelItems: MutableList<WheelItem> = ArrayList()

    private var lastResultPos: Int = 0

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

        binding.mainWheel.setLuckyWheelReachTheTarget {
            Toast.makeText(activity, wheelItems[lastResultPos].text, Toast.LENGTH_LONG).show()
        }


        binding.btnSpin.setOnClickListener {
            lastResultPos = Random.nextInt(0, wheelItems.size)
            binding.mainWheel.rotateWheelTo(lastResultPos + 1)
        }

        binding.mainWheel.setOnLongClickListener {
            val deleteDialogFragment = DeleteDialogFragment(wheelItems)
            deleteDialogFragment.show(childFragmentManager,"Delete Fragment")
            Toast.makeText(activity, "LongCLickedddddd", Toast.LENGTH_LONG).show()
            true
        }
    }


    private fun prepareData() {
        wheelItems.add(
            WheelItem(
                Color.CYAN,
                BitmapFactory.decodeResource(resources, R.drawable.ic_action_name),
                "Cơm tấm"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.MAGENTA,
                BitmapFactory.decodeResource(resources, R.drawable.ic_action_name),
                "Bún bò"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.DKGRAY,
                BitmapFactory.decodeResource(resources, R.drawable.ic_action_name),
                "Phở"
            )
        )

        binding.mainWheel.addWheelItems(wheelItems)
    }

    override fun onPositiveButtonClicked(selectedItems: ArrayList<Int>) {
        var new_wheelItems: MutableList<WheelItem> = ArrayList()

        for (index in selectedItems)
            new_wheelItems.add(wheelItems[index])

        wheelItems.clear()

        for (item in new_wheelItems)
            wheelItems.add(item)

        binding.mainWheel.addWheelItems(wheelItems)
    }

}
