package com.android.food_app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {


    val showDilog=MutableLiveData<String>()

}