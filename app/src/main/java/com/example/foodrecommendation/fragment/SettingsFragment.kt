package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.foodrecommendation.R
import com.example.foodrecommendation.model.HistoryViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {
    private val historyViewModel by activityViewModels<HistoryViewModel>()


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val logout = findPreference<Preference>("userLogout")
        logout?.setOnPreferenceClickListener { userLogout() }

        val history = findPreference<Preference>("userHistory")
        history?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.historyFragment)
            true
        }

        val clearHistory = findPreference<Preference>("clearUserHistory")
        clearHistory?.setOnPreferenceClickListener {
            historyViewModel.clearUserHistory()
            Toast.makeText(requireContext(), "History deleted!", Toast.LENGTH_LONG).show()
            true
        }
    }

    private fun userLogout(): Boolean {
        if (FirebaseAuth.getInstance().currentUser?.isAnonymous == true)
            AuthUI.getInstance().delete(requireContext()).addOnSuccessListener {
                Toast.makeText(
                    this.requireContext(),
                    "Log out",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
        else AuthUI.getInstance().signOut(requireContext()).addOnSuccessListener {
            Toast.makeText(
                this.requireContext(),
                "Log out",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()
        }
        return true
    }
}