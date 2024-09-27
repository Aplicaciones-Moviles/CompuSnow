package com.example.compusnow.model.service.impl

import com.example.compusnow.model.User
import com.example.compusnow.model.service.AccountService
import com.example.compusnow.model.service.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import android.util.Log

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {


    companion object {
        private const val USER_COLLECTION = "users"
    }
}

