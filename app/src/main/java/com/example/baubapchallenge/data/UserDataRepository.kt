package com.example.baubapchallenge.data

import com.example.baubapchallenge.domain.models.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    fun getUserData(userId: String): Flow<Result<UserData>>
}