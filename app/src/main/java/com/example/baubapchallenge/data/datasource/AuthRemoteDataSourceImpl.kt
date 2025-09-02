package com.example.baubapchallenge.data.datasource

import com.example.baubapchallenge.data.datasource.firebase.FirestoreClient
import com.example.baubapchallenge.data.exception.AuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.security.MessageDigest
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val client: FirestoreClient
) : AuthRemoteDataSource {

    override fun signUp(phone: String, curp: String, password: String): Flow<Result<String>> = flow {
        val hashedPassword = hashPassword(password)

        val exists = client.userExistsByPhoneOrCurp(phone, curp)
        if (exists) {
            emit(Result.failure(AuthException.UserAlreadyExistException()))
            return@flow
        }

        val id = client.createUser(phone, curp, hashedPassword)
        emit(Result.success(id))
    }.catch { e ->
        emit(Result.failure(handleException(e, isSignUp = true)))
    }

    override fun signIn(identifier: String, password: String): Flow<Result<String>> = flow {
        val hashedPassword = hashPassword(password)
        val userId = client.findUserId(identifier, hashedPassword)

        if (userId != null) emit(Result.success(userId))
        else emit(Result.failure(AuthException.SignInException()))
    }.catch { e ->
        emit(Result.failure(handleException(e, isSignUp = false)))
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    private fun handleException(exception: Throwable, isSignUp: Boolean) = when {
        exception is FirebaseFirestoreException && exception.code == FirebaseFirestoreException.Code.UNAVAILABLE -> AuthException.NetworkException()
        isSignUp -> AuthException.SignUpException()
        else -> AuthException.SignInException()
    }
}
