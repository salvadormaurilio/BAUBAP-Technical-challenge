package com.example.baubapchallenge.domain

import com.example.baubapchallenge.data.AuthRepository
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) : SignInUseCase {

    override fun invoke(identifier: String, password: String) = authRepository.signIn(identifier = identifier, password = password)
}