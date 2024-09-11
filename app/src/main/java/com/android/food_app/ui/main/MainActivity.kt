package com.android.food_app.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.android.food_app.base.BaseActivity
import com.android.testy_food.R
import com.android.testy_food.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun get_layout(): Int {
        return R.layout.activity_main
    }

    override fun initial_viewModel(): MainViewModel {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }
}