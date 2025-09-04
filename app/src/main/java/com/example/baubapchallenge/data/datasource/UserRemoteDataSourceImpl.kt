package com.example.baubapchallenge.data.datasource

import com.example.baubapchallenge.data.datasource.firebase.FirestoreClient
import com.example.baubapchallenge.data.exception.UserDataException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val client: FirestoreClient
) : UserRemoteDataSource {

    override fun getUserData(userId: String) = flow {
        val userData = client.getUserData(userId)

        if (userData != null) emit(Result.success(userData))
        else emit(Result.failure(UserDataException.DataException()))
    }.catch { e ->
        emit(Result.failure(handleException(e)))
    }

    private fun handleException(exception: Throwable) = when {
        exception is FirebaseFirestoreException && exception.code == FirebaseFirestoreException.Code.UNAVAILABLE -> UserDataException.NetworkException()
        else -> UserDataException.DataException()
    }
}