package com.example.baubapchallenge.ui.signin

import com.example.baubapchallenge.core.extensions.empty

data class SignInUiState(
    val identifier: String = String.empty(),
    val pin: String = String.empty(),
    val showIdentifierError: Boolean = false,
    val showPinError: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface SignInUiIntent {
    data class IdentifierChanged(val value: String) : SignInUiIntent
    data class PinChanged(val value: String) : SignInUiIntent
    data object ConfirmSignIn : SignInUiIntent
}

sealed interface SignInUiEffect {
    data class Success(val userId: String) : SignInUiEffect
    data class Message(val error: Throwable) : SignInUiEffect
}