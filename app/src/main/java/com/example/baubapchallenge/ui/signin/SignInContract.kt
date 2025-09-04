package com.example.baubapchallenge.ui.signin

data class SignInUiState(
    val identifier: String = "",
    val pin: String = "",
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