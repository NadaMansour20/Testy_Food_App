package com.android.food_app.ui.auth.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ObservableField
import com.android.food_app.base.BaseViewModel
import com.android.food_app.firebase.User
import com.android.food_app.firebase.signInToFirebase
import com.android.food_app.ui.main.MainActivity
import com.android.testy_food.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.toObject
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

        showProgress.value=true
        auth.signInWithEmailAndPassword(Email.get()!!,Pass.get()!!).addOnCompleteListener {
            if(!it.isSuccessful){
                Log.e("login",it.exception?.localizedMessage!!)

                showDilog.value=it.exception?.localizedMessage

            }
            else{
                showProgress.value=false
                Log.e("login","successfullyyyyyyyyyyy")
               checkAccount(it.result.user!!.uid)

            }
        }

    }

    fun checkAccount(userId:String){

        signInToFirebase(userId, {
            //onSuccess listener

            val returnUser=it.toObject(User::class.java)
            if(returnUser==null) {
                showDilog.value="Invalid Email or Password :("
            }
            else{
                showDilog.value = "Login Successfully :)"

            }

        }, {
            showDilog.value=it.localizedMessage

        })
    }
    // This method handles Firebase authentication with the Google account
    fun updateUi(account: GoogleSignInAccount) {
        showProgress.value=true
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("GoogleSignIn", "Sign-in successful")

                    showDilog.value = "Login Successfully :)"
                    showProgress.value=false
                } else {
                    Log.e("GoogleSignIn", "Sign-in failed", task.exception)

                    showDilog.value = "Sign-in failed"+task.exception

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