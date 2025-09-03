package com.example.baubapchallenge.data

import com.example.baubapchallenge.data.datasource.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authRemoteDataSource: AuthRemoteDataSource) : AuthRepository {

    override fun signUp(phone: String, curp: String, password: String) =
        authRemoteDataSource.signUp(phone = phone, curp = curp, password = password)

    override fun signIn(identifier: String, password: String) =
        authRemoteDataSource.signIn(identifier = identifier, password = password)
}