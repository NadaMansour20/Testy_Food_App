package com.android.food_app.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.food_app.base.BaseActivity
import com.android.food_app.fragment.FavouriteFragment
import com.android.food_app.fragment.HomeFragment
import com.android.food_app.fragment.UserProfileFragment
import com.android.testy_food.R
import com.android.testy_food.databinding.ActivityMainBinding

class MainActivity :BaseActivity<MainViewModel,ActivityMainBinding>() {


     var homeFragment= HomeFragment()
     var profileFragment=UserProfileFragment()
     var favouriteFragment= FavouriteFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //default
        //pushFragment(homeFragment)

        botton_navigation_click()
    }

    override fun get_layout(): Int {
        return R.layout.activity_main
    }

    override fun initial_viewModel():MainViewModel {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun botton_navigation_click(){
        databinding.bottomNavigation.setOnItemSelectedListener {
            if(it.itemId==R.layout.fragment_home){
                pushFragment(homeFragment)
            }
            if(it.itemId==R.layout.fragment_favourite){
                pushFragment(favouriteFragment)
            }
            if(it.itemId==R.layout.fragment_user_profile){
                pushFragment(profileFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    fun pushFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction().replace(R.id.framelayout,fragment).commit()
    }

}