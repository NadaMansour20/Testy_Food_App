package com.android.food_app.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("android:error")
fun error(textInputLayout: TextInputLayout,error:String?)
{
    textInputLayout.error=error
}