package com.example.baubapchallenge.data

import com.example.baubapchallenge.data.datasource.UserRemoteDataSource
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserDataRepository {

    override fun getUserData(userId: String) = userRemoteDataSource.getUserData(userId)
}