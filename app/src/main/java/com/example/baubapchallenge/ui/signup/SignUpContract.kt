package com.example.baubapchallenge.ui.signup

data class SignUpUiState(
    val phone: String = "",
    val curp: String = "",
    val pin: String = "",
    val showPhoneError: Boolean = false,
    val showCurpError: Boolean = false,
    val showPinError: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface SignUpUiIntent {
    data class PhoneChanged(val value: String) : SignUpUiIntent
    data class CurpChanged(val value: String) : SignUpUiIntent
    data class PinChanged(val value: String) : SignUpUiIntent
    data object ConfirmSignUp : SignUpUiIntent
}

sealed interface SignUpUiEffect {
    data class Success(val userId: String) : SignUpUiEffect
    data class Message(val error: Throwable) : SignUpUiEffect
}
