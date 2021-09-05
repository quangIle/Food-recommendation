package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.foodrecommendation.R
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val logout = findPreference<Preference>("userLogout")
        logout?.setOnPreferenceClickListener { userLogout() }
    }

    private fun userLogout(): Boolean {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(
            this.requireContext(),
            "Log out",
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigateUp()
        return true
    }
}