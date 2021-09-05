package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodrecommendation.R
import com.example.foodrecommendation.adapter.HistoryListAdapter
import com.example.foodrecommendation.databinding.FragmentHistoryBinding
import com.example.foodrecommendation.model.HistoryViewModel
import np.com.susanthapa.curved_bottom_navigation.CurvedBottomNavigationView

class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding
    private val historyViewModel by activityViewModels<HistoryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        this.requireActivity()
            .findViewById<CurvedBottomNavigationView>(R.id.bottom_navigation)
            .visibility = View.INVISIBLE

        binding.historyCancelButton.setOnClickListener{
            findNavController().navigateUp()
        }
        historyViewModel.loadUserHistory()
        observeHistoryState()
        return binding.root
    }

    private fun observeHistoryState() {
        historyViewModel.historyState.observe(viewLifecycleOwner, { historyState ->
            when (historyState) {
                historyViewModel.COMPLETED -> {
                    binding.todayList.adapter = HistoryListAdapter(historyViewModel.todayList)
                    binding.yesterdayList.adapter = HistoryListAdapter(historyViewModel.yesterdayList)
                    binding.weekList.adapter = HistoryListAdapter(historyViewModel.weekList)
                    binding.olderList.adapter = HistoryListAdapter(historyViewModel.olderList)

                }
                historyViewModel.LOADING -> {
                }
                else -> {
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.requireActivity()
            .findViewById<CurvedBottomNavigationView>(R.id.bottom_navigation)
            .visibility = View.VISIBLE
    }
}