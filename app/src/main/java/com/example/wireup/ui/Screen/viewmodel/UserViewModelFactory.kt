package com.example.wireup.ui.Screen.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.login.LoginScreenViewModel
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class UserViewModelFactory(private val repository: FirestoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LoginScreenViewModelFactory(private val activity: Activity) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginScreenViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

