package com.android.food_app.ui.profile

import android.util.Log
import androidx.databinding.ObservableField
import com.android.food_app.base.BaseViewModel
import com.android.food_app.firebase.User
import com.android.food_app.firebase.getUserDataFromFirestore
import com.android.food_app.firebase.updateUserDataInFirestore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ProfileViewModel:BaseViewModel() {


    var userName=ObservableField<String>()
    var emaill=ObservableField<String>()
    var password=ObservableField<String>()

    fun get_user_data(){
        val userId=FirebaseAuth.getInstance().currentUser?.uid

        getUserDataFromFirestore(
            userId!!, {
                if(it.exists()){
                    val userObject=it.toObject(User::class.java)
                    userName.set(userObject?.userName)
                    emaill.set(userObject?.email)
                    password.set(userObject?.pass)

                }
            },
            {

            }
        )
    }
    fun update_user_data() {
        showProgress.value=true
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            // Map of fields to update
            val updatedData = mutableMapOf<String, Any>()

            userName.get()?.let { updatedData["name"] = it }
            emaill.get()?.let { updatedData["email"] = it }
            password.get()?.let { updatedData["pass"] = it }

            updateUserDataInFirestore(
                userId,
                updatedData,
                {
                    showProgress.value=false
                    showDilog.value="User data updated successfully :)"
                },
                { exception ->

                    showDilog.value="Error updating user data: ${exception.message} :("
                }
            )
        } else {
        }
    }

}