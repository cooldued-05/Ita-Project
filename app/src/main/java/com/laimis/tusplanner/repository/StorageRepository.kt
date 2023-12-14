package com.laimis.tusplanner.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore

const val Course_Name_Ref = "Course_Name"

class StorageRepository(){

    val user = Firebase.auth.currentUser
    fun hasUser():Boolean = Firebase.auth.currentUser != null

    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()

    private val CourseNRef:CollectionReference = Firebase.firestore.collection(Course_Name_Ref)

}
sealed class Resource<T>(
    val data:T? = null,
    val throwable: Throwable? = null,
    ){
    class Loading<T>:Resource<T>()
    class Success<T>(data: T?):Resource<T>()
    class Error<T>(throwable: Throwable?):Resource<T>(throwable = throwable)
}