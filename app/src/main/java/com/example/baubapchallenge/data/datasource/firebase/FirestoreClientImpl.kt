package com.example.baubapchallenge.data.datasource.firebase

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreClientImpl @Inject constructor(private val firestore: FirebaseFirestore) : FirestoreClient {

    override suspend fun userExistsByPhoneOrCurp(phone: String, curp: String): Boolean {
        val queryUser = firestore.collection(USERS_COLLECTION)
            .where(
                Filter.or(
                    Filter.equalTo(PHONE_FIELD, phone),
                    Filter.equalTo(CURP_FIELD, curp)
                )
            )
            .limit(1)
            .get()
            .await()

        return queryUser.isEmpty.not()
    }

    override suspend fun createUser(phone: String, curp: String, hashedPassword: String): String {
        val userData = mapOf(
            PHONE_FIELD to phone,
            CURP_FIELD to curp,
            PASSWORD_FIELD to hashedPassword
        )

        val userDocument = firestore.collection(USERS_COLLECTION)
            .add(userData)
            .await()

        return userDocument.id
    }

    override suspend fun findUserId(identifier: String, hashedPassword: String): String? {
        val queryUser = firestore.collection(USERS_COLLECTION)
            .where(
                Filter.and(
                    Filter.or(
                        Filter.equalTo(PHONE_FIELD, identifier),
                        Filter.equalTo(CURP_FIELD, identifier)
                    ),
                    Filter.equalTo(PASSWORD_FIELD, hashedPassword)
                )
            )
            .limit(1)
            .get()
            .await()

        return queryUser.documents.firstOrNull()?.id
    }

    private companion object {
        const val USERS_COLLECTION = "users"
        const val PHONE_FIELD = "phone"
        const val CURP_FIELD = "curp"
        const val PASSWORD_FIELD = "password"
    }
}
