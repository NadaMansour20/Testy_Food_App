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


data class FavouritFood(

    var id:String?=null,
    val imgUrl:String?=null,
    val foodName:String?=null,
    val youtubeUrl:String?=null,

    ){
    companion object {
        const val favouritTable = "favouriteFood"
    }

}
