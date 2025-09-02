package com.example.baubapchallenge.data.datasource

import com.example.baubapchallenge.data.exception.AuthException
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(private val firestore: FirebaseFirestore) : AuthRemoteDataSource {

    override fun signUp(phone: String, curp: String, password: String): Flow<Result<String>> = flow {
        try {
            val usersCollection = firestore.collection(COLLECTION_USERS)
            val hashedPassword = hashPassword(password)

            val existingUser = usersCollection
                .where(
                    Filter.or(
                        Filter.equalTo(FIELD_PHONE, phone),
                        Filter.equalTo(FIELD_CURP, curp)
                    )
                )
                .limit(1)
                .get()
                .await()

            if (existingUser.isEmpty.not()) {
                emit(Result.failure(AuthException.UserAlreadyExistException()))
            }

            val newUser = mapOf(
                FIELD_PHONE to phone,
                FIELD_CURP to curp,
                FIELD_PASSWORD to hashedPassword
            )

            val userRef = usersCollection.add(newUser).await()
            emit(Result.success(userRef.id))

        } catch (e: Exception) {
            emit(Result.failure(handleException(e, isSignUp = true)))
        }
    }

    override fun signIn(identifier: String, password: String): Flow<Result<String>> = flow {
        try {
            val usersCollection = firestore.collection(COLLECTION_USERS)
            val hashedPassword = hashPassword(password)

            val queryUser = usersCollection
                .where(
                    Filter.and(
                        Filter.or(
                            Filter.equalTo(FIELD_PHONE, identifier),
                            Filter.equalTo(FIELD_CURP, identifier)
                        ),
                        Filter.equalTo(FIELD_PASSWORD, hashedPassword)
                    )
                )
                .limit(1)
                .get()
                .await()

            val userRef = queryUser.documents.firstOrNull()

            if (userRef != null) {
                emit(Result.success(userRef.id))
            } else {
                emit(Result.failure(AuthException.SignInException()))
            }

        } catch (e: Exception) {
            emit(Result.failure(handleException(e, isSignUp = false)))
        }
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    private fun handleException(exception: Exception, isSignUp: Boolean) = when {
        exception is FirebaseFirestoreException && exception.code == FirebaseFirestoreException.Code.UNAVAILABLE -> AuthException.NetworkException()
        isSignUp -> AuthException.SignUpException()
        else -> AuthException.SignInException()
    }

    private companion object {
        const val COLLECTION_USERS = "users"
        const val FIELD_PHONE = "phone"
        const val FIELD_CURP = "curp"
        const val FIELD_PASSWORD = "password"
    }
}
