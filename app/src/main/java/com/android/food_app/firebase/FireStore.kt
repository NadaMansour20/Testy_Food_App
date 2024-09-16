package com.android.food_app.firebase

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//register
fun addUserToFirestore(
    user:User,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {

    val fireStore=Firebase.firestore

    val collection = fireStore.collection(User.userTable)

    val document = collection.document(user.userId!!)

    //new document --> new object
    document.set(user).addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)


}

// login
fun signInToFirebase(
    id: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {

    val fireStore=Firebase.firestore

    val collection = fireStore.collection(User.userTable)

    val document = collection.document(id)

    document.get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
}
fun getUserDataFromFirestore(
    userId: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {
    val fireStore = Firebase.firestore
    val collection = fireStore.collection(User.userTable)

    // Fetch the document with the given userId
    val document = collection.document(userId)

    document.get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
}
fun updateUserDataInFirestore(
    userId: String,
    updatedData: Map<String, Any>,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val fireStore = Firebase.firestore
    val collection = fireStore.collection(User.userTable)

    // Fetch the document with the given userId
    val document = collection.document(userId)

    document.update(updatedData).addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}


fun addFavouritFood(
    favouritFood: FavouritFood,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val fireStore = Firebase.firestore
    val collection = fireStore.collection(FavouritFood.favouritTable)

    val document = collection.document()

    favouritFood.id = document.id
    document.set(favouritFood).addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)


}

