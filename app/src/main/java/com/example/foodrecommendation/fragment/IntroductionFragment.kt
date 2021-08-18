package com.example.foodrecommendation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodrecommendation.R
import com.example.foodrecommendation.databinding.FragmentIntroductionBinding
import com.example.foodrecommendation.model.LoginViewModel
import com.firebase.ui.auth.AuthUI


class IntroductionFragment : Fragment() {
    private lateinit var binding: FragmentIntroductionBinding
    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroductionBinding.inflate(layoutInflater, container, false)
        observeAuthenticationState()
        binding.logout.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
        }
        return binding.root
    }

    private fun observeAuthenticationState() {
        loginViewModel.authenticationState.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {

                    // TODO 2. If the user is logged in,
                    // you can customize the welcome message they see by
                    // utilizing the getFactWithPersonalization() function provided

                }
                else -> {
                    // TODO 3. Lastly, if there is no logged-in user,
                    // auth_button should display Login and
                    //  launch the sign in screen when clicked.
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        })
    }
}