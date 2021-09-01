package com.example.foodrecommendation.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    class FirebaseUserLiveData : LiveData<FirebaseUser?>() {
        private val firebaseAuth = FirebaseAuth.getInstance()

        private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            Log.d("user", firebaseAuth.currentUser.toString())
            value = firebaseAuth.currentUser
        }

        // When this object has an active observer, start observing the FirebaseAuth state to see if
        // there is currently a logged in user.
        override fun onActive() {
            firebaseAuth.addAuthStateListener(authStateListener)
        }

        // When this object no longer has an active observer, stop observing the FirebaseAuth state to
        // prevent memory leaks.
        override fun onInactive() {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}