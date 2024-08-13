package com.example.wireup.ui.Screen.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wireup.model.MUser
import com.example.wireup.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth


class UserViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    fun getUserData(): LiveData<MUser> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.getUserData(uuid)
    }

    fun updateUserData(userData: MUser): LiveData<Boolean> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.updateUserData(uuid, userData)
    }

    fun uploadImageToFirebaseStorage(imageUri: Uri): LiveData<String> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.uploadImageToFirebaseStorage(imageUri, userId)
    }

//    fun updateUserProfileImage(userId: String, imageUrl: String): LiveData<Boolean> {
//        val userData = MUser()
//        userData.profileImage = imageUrl
//        return UserViewModel(FirestoreRepository()).updateUserData(userData)
//    }

    fun updateUserProfileImage(imageUrl: String): LiveData<Boolean> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val liveData = MutableLiveData<Boolean>()
        getUserData().observeForever { userData ->
            userData?.profileImage = imageUrl
            updateUserData(userData).observeForever { isSuccess ->
                liveData.value = isSuccess
            }
        }
        return liveData
    }

}

