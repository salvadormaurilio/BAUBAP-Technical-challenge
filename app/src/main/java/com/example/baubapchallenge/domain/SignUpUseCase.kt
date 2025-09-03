package com.example.baubapchallenge.domain

import kotlinx.coroutines.flow.Flow

interface SignUpUseCase {

    operator fun invoke(phone: String, curp: String, password: String): Flow<Result<String>>
}
