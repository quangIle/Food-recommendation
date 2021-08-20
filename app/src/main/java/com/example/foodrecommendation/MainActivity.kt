package com.example.foodrecommendation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setupWithNavController(navController)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_list -> {
                    navController.navigate(R.id.foodListFragment)
                    true
                }
                R.id.action_wheel -> {
                    navController.navigate(R.id.wheelFragment)
                    true
                }
                R.id.action_verticalList -> {
                    navController.navigate(R.id.verticalListFragment)
                    true
                }
                else -> false
            }
        }

        supportActionBar?.hide()
    }

}