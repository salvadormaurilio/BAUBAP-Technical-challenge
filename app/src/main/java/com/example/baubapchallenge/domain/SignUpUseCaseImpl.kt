package com.example.baubapchallenge.domain

import com.example.baubapchallenge.data.AuthRepository
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) : SignUpUseCase {

    override fun invoke(phone: String, curp: String, password: String) =
        authRepository.signUp(phone = phone, curp = curp, password = password)
}
