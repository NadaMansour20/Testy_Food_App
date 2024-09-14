package com.android.food_app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {



    val showProgress=MutableLiveData<Boolean>()
    val showDilog=MutableLiveData<String>()

}