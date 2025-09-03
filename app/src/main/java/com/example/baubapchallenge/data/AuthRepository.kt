package com.example.baubapchallenge.data

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(phone: String, curp: String, password: String): Flow<Result<String>>

    fun signIn(identifier: String, password: String): Flow<Result<String>>
}
