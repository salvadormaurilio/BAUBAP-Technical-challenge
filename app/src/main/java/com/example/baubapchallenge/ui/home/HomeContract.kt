package com.example.baubapchallenge.ui.home

import com.example.baubapchallenge.domain.models.UserData


data class HomeUiState(
    val isLoading: Boolean = false,
    val userData: UserData? = null,
    val error: Throwable? = null
)

sealed interface HomeUiIntent {
    data class GetUserData(val userId: String) : HomeUiIntent
}