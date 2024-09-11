package com.android.food_app.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<VM:BaseViewModel,DB: ViewDataBinding> : AppCompatActivity() {


    lateinit var databinding:DB
    lateinit var viewModel:VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //splash screen
        installSplashScreen()

        databinding= DataBindingUtil.setContentView(this,get_layout())

        viewModel=initial_viewModel()


    }



   abstract fun get_layout():Int

    abstract fun initial_viewModel():VM
}