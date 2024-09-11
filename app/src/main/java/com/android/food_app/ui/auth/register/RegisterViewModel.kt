package com.android.food_app.ui.auth.register

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.android.food_app.base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : BaseViewModel() {


    val Email= ObservableField<String>()
    val Pass= ObservableField<String>()
    val userName= ObservableField<String>()


    val EmailError= ObservableField<String>()
    val PassError= ObservableField<String>()
    val userNameError= ObservableField<String>()





    fun register(){

        if(validate()){

            signUp()
        }
    }

    val auth= Firebase.auth

    fun signUp(){

        auth.createUserWithEmailAndPassword(Email.get()!!,Pass.get()!!).addOnCompleteListener {
            if(!it.isSuccessful){
                Log.e("register",it.exception?.localizedMessage!!)
                showDilog.value=it.exception?.localizedMessage
            }
            else{
                showDilog.value="Register Successfully :)"
                Log.e("register","successfullyyyyyyyyyyy")

            }
        }

    }


    fun validate():Boolean{

        var valid=true

        if(userName.get().isNullOrBlank()){

            valid=false
            userNameError.set("Please enter userName")
        }
        else{
            userNameError.set(null)
        }

        if(Email.get().isNullOrBlank()){

            valid=false
            EmailError.set("Please enter email")
        }
        else{
            EmailError.set(null)
        }

        if(Pass.get().isNullOrBlank()){

            valid=false
            PassError.set("Please enter password")
        }
        else{
            PassError.set(null)
        }

        return valid
    }



}