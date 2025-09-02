package com.example.baubapchallenge.data.datasource.firebase

interface FirestoreClient {

    suspend fun userExistsByPhoneOrCurp(phone: String, curp: String): Boolean

    suspend fun createUser(phone: String, curp: String, hashedPassword: String): String

    suspend fun findUserId(identifier: String, hashedPassword: String): String?
}