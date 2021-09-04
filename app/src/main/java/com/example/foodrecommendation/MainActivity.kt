package com.example.foodrecommendation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.foodrecommendation.model.LoginViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem
import np.com.susanthapa.curved_bottom_navigation.CurvedBottomNavigationView

class MainActivity : AppCompatActivity() {
    private val TAG = "Quang"
    private lateinit var navController: NavController
    private lateinit var bottomNavigation: CurvedBottomNavigationView
    private val loginViewModel by viewModels<LoginViewModel>()
    private var launchSignIn = true
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    )
    { result ->
        onSignInResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        observeAuthenticationState()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //setupActionBarWithNavController(navController)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setMenuItems(menuItems, 0)
        bottomNavigation.setupWithNavController(navController)
        //bottomNavigation.visibility = View.INVISIBLE

    }


    private fun onSuccess() {
        Log.d(TAG, "Login successful! User token: ${FirebaseAuth.getInstance().currentUser}")
    }

    private fun onExitApp() {
        Log.d(TAG, "Exit app")
        this.finishAndRemoveTask()
    }

    private fun launchSignInFlow() {
        val signInIntent = createSignInIntent()
        signInLauncher.launch(signInIntent)
    }

    private fun createSignInIntent(): Intent {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
            //AuthUI.IdpConfig.FacebookBuilder().build(),
            //AuthUI.IdpConfig.TwitterBuilder().build())
        )
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
    }

    private fun onSignInResult(
        result: FirebaseAuthUIAuthenticationResult
    ) {
        val response = result.idpResponse
        if (result.resultCode == Activity.RESULT_OK)
            onSuccess()
        else {
            Log.d(TAG, result.toString())
            if (response == null)
                onExitApp()
        }
    }

    private fun observeAuthenticationState() {
        loginViewModel.authenticationState.observe(this, { authenticationState ->
            Log.d(TAG, authenticationState.toString())
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    Log.d(
                        TAG,
                        "Already signed in! Current user token: ${FirebaseAuth.getInstance().currentUser?.displayName}"
                    )
                    launchSignIn = true
                    bottomNavigation.visibility = View.VISIBLE
                }
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    if (launchSignIn)
                        launchSignInFlow()

                    launchSignIn = false
                    bottomNavigation.visibility = View.INVISIBLE
                }
                else -> {
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private val menuItems = arrayOf(
        CbnMenuItem(
            R.drawable.ic_camera,
            R.drawable.avd_camera,
            R.id.foodListFragment
        ),
        CbnMenuItem(
            R.drawable.ic_dashboard, // the icon
            R.drawable.avd_dashboard, // the AVD that will be shown in FAB
            R.id.verticalListFragment // optional if you use Jetpack Navigation
        ),
        CbnMenuItem(
            R.drawable.ic_settings,
            R.drawable.avd_settings,
            R.id.settingsFragment
        )
    )

}
