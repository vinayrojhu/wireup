package com.example.wireup.ui.Screen.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginScreenViewModel: ViewModel() {
     val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading


    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) = viewModelScope.launch {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Log.d("FB", "signInWithEmailAndPassword: Yayayay! ${result.toString()}")
            home()
        } catch (ex: Exception) {
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }
    }

//    fun createUserWithEmailAndPassword(email: String, password: String, name: String, home: () -> Unit) = viewModelScope.launch {
//        try {
//            val result = auth.createUserWithEmailAndPassword(email, password).await()
//            createUser(name)
//            home()
//        } catch (ex: Exception) {
//            Log.d("FB", "createUserWithEmailAndPassword: ${ex.message}")
//        }
//    }
fun createUserWithEmailAndPassword(email: String, password: String, name: String, home: () -> Unit) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                val profile_image = ""
                val user = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "userID" to userId,
                    "profile_image" to profile_image
                )
                FirebaseFirestore.getInstance().collection("users").document(userId!!).set(user)
                home()
            } else {
                // Handle error
            }
        }
}

//    private fun createUser(displayName: String?) {
//        val userId = auth.currentUser?.uid
//
//        val user = MUser(userId = userId.toString(),
//            displayName = displayName.toString(),
//            id = null).toMap()
//
//
//        FirebaseFirestore.getInstance().collection("users")
//            .document(userId.toString())
//            .set(user)
//
//    }


}