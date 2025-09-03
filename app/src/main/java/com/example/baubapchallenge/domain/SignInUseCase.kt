package com.example.baubapchallenge.domain

import kotlinx.coroutines.flow.Flow

interface SignInUseCase {

    operator fun invoke(identifier: String, password: String): Flow<Result<String>>
}
