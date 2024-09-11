package com.android.food_app.ui.auth.login

import android.app.Activity
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ObservableField
import com.android.food_app.base.BaseViewModel
import com.android.testy_food.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginViewModel:BaseViewModel() {


    val Email=ObservableField<String>()
    val Pass=ObservableField<String>()

    val EmailError=ObservableField<String>()
    val PassError=ObservableField<String>()



    fun login(){

        if(validate()){

            signIn()
        }
    }

    val auth=Firebase.auth

    fun signIn(){

        auth.signInWithEmailAndPassword(Email.get()!!,Pass.get()!!).addOnCompleteListener {
            if(!it.isSuccessful){
                Log.e("login",it.exception?.localizedMessage!!)

                showDilog.value=it.exception?.localizedMessage

            }
            else{
                Log.e("login","successfullyyyyyyyyyyy")
                showDilog.value="Login Successfully :)"

            }
        }

    }

    fun updateUi(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("GoogleSignIn", "Sign-in successful")
                } else {
                    Log.e("GoogleSignIn", "Sign-in failed", task.exception)
                }
            }
    }

    fun validate():Boolean{

        var valid=true

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