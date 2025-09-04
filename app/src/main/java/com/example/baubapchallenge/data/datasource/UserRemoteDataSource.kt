package com.example.baubapchallenge.data.datasource

import com.example.baubapchallenge.domain.models.UserData
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    fun getUserData(userId: String): Flow<Result<UserData>>
}