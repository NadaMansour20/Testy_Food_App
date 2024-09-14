package com.android.food_app.ui

import android.app.Application
import android.content.Intent
import com.android.food_app.ui.auth.login.LoginActivity
import com.android.food_app.ui.main.MainActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

    }
}