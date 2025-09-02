package com.example.baubapchallenge.data.datasource

import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {

    fun signUp(phone: String, curp: String, password: String): Flow<Result<String>>

    fun signIn(identifier: String, password: String): Flow<Result<String>>
}