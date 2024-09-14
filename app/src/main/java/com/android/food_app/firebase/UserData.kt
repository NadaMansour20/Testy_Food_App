package com.android.food_app.firebase


//user table to store it in firebase
data class User(
    val userId:String?=null,
    val userName:String?=null,
    val email:String?=null,
    val pass:String?=null


){

    companion object {
        const val userTable = "user"
    }
}
