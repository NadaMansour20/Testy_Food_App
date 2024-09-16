package com.android.food_app.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.android.food_app.base.BaseActivity
import com.android.food_app.fragment.FavouriteFragment
import com.android.food_app.fragment.HomeFragment
import com.android.food_app.ui.profile.UserProfileFragment
import com.android.testy_food.R
import com.android.testy_food.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity :BaseActivity<MainViewModel,ActivityMainBinding>() {


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding.mvm=viewModel

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(databinding.bottomNavigation, navController)


        databinding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.favouriteFragment -> {
                    navController.navigate(R.id.favouriteFragment)
                    true
                }
                R.id.userProfileFragment -> {
                    navController.navigate(R.id.userProfileFragment)
                    true
                }
                else -> false
            }
        }

    }



    override fun get_layout(): Int {
        return R.layout.activity_main
    }

    override fun initial_viewModel():MainViewModel {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }


}