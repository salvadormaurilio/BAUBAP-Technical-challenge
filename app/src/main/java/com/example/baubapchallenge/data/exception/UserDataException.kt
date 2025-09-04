package com.example.baubapchallenge.data.exception

sealed class UserDataException(message: String) : Exception(message) {
    data class DataException(override val message: String = "There was an error getting the user data.") : UserDataException(message)
    data class NetworkException(override val message: String = "There was a network error. Please check your connection.") :
        UserDataException(message)
}
