package com.example.wireup.ui.Screen.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wireup.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginScreenViewModel(private val activity: Activity): ViewModel() {

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
    fun createUserWithEmailAndPassword(email: String, password: String, name: String, home: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                val profile_image = ""
                val uniqueId = "Create UID"
                val followers = mutableListOf<String>()
                val following = mutableListOf<String>()
                val user = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "userID" to userId,
                    "profile_image" to profile_image,
                    "uniqueId" to uniqueId,
                    "followers" to followers,
                    "following" to following
                )
                FirebaseFirestore.getInstance().collection("users").document(userId!!).set(user)
                home()
            } else {
                // Handle error
            }
        }
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(activity, gso)
    }

//    fun signInWithGoogle() {
//        googleSignInClient.signInIntent.also { intent ->
//            startActivityForResult(activity, intent,123, Bundle())
//        }
//    }

    fun signInWithGoogle(): Intent {
        val intent = googleSignInClient.signInIntent
        return intent
    }



}