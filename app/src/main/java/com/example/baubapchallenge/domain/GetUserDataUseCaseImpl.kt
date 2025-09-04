package com.example.baubapchallenge.domain

import com.example.baubapchallenge.data.UserDataRepository
import javax.inject.Inject

class GetUserDataUseCaseImpl @Inject constructor(
    private val userDataRepository: UserDataRepository
) : GetUserDataUseCase {

    override operator fun invoke(userId: String) = userDataRepository.getUserData(userId)
}