package com.example.baubapchallenge.data

import com.example.baubapchallenge.data.datasource.UserDataRemoteDataSource
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userDataRemoteDataSource: UserDataRemoteDataSource
) : UserDataRepository {

    override fun getUserData(userId: String) = userDataRemoteDataSource.getUserData(userId)
}