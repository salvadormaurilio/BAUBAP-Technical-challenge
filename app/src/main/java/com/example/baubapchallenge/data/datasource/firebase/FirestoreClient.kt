package com.example.baubapchallenge.data.datasource.firebase

import com.example.baubapchallenge.domain.models.UserData

interface FirestoreClient {

    suspend fun userExistsByPhoneOrCurp(phone: String, curp: String): Boolean

    suspend fun createUser(phone: String, curp: String, hashedPassword: String): String

    suspend fun findUserId(identifier: String, hashedPassword: String): String?

    suspend fun getUserData(userId: String): UserData?
}