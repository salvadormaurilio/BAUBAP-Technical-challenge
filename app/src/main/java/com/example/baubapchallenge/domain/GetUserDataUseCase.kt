package com.example.baubapchallenge.domain

import com.example.baubapchallenge.domain.models.UserData
import kotlinx.coroutines.flow.Flow

interface GetUserDataUseCase {

    operator fun invoke(userId: String): Flow<Result<UserData>>
}