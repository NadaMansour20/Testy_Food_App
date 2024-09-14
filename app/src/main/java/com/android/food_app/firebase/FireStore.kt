package com.android.food_app.firebase

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
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