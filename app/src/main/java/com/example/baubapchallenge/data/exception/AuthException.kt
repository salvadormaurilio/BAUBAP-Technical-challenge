package com.example.baubapchallenge.data.exception

sealed class AuthException(message: String) : Exception(message) {
    data class SignUpException(override val message: String = "Invalid Credentials at Sing Up") : AuthException(message)
    data class UserAlreadyExistException(override val message: String = "User al already exist in data base") : AuthException(message)
    data class SignInException(override val message: String = "Some error happened with the Sign In") : AuthException(message)
    data class NetworkException(override val message: String = "There was a network error. Please check your connection.") :
        AuthException(message)
}
