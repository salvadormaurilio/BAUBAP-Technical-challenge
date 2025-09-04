package com.example.baubapchallenge.di

import com.example.baubapchallenge.data.AuthRepository
import com.example.baubapchallenge.data.AuthRepositoryImpl
import com.example.baubapchallenge.data.UserDataRepository
import com.example.baubapchallenge.data.UserDataRepositoryImpl
import com.example.baubapchallenge.data.datasource.AuthRemoteDataSource
import com.example.baubapchallenge.data.datasource.AuthRemoteDataSourceImpl
import com.example.baubapchallenge.data.datasource.UserDataRemoteDataSource
import com.example.baubapchallenge.data.datasource.UserDataRemoteDataSourceImpl
import com.example.baubapchallenge.data.datasource.firebase.FirestoreClient
import com.example.baubapchallenge.data.datasource.firebase.FirestoreClientImpl
import com.example.baubapchallenge.domain.GetUserDataUseCase
import com.example.baubapchallenge.domain.GetUserDataUseCaseImpl
import com.example.baubapchallenge.domain.SignInUseCase
import com.example.baubapchallenge.domain.SignInUseCaseImpl
import com.example.baubapchallenge.domain.SignUpUseCase
import com.example.baubapchallenge.domain.SignUpUseCaseImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindFirestoreClient(impl: FirestoreClientImpl): FirestoreClient

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCountriesRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    abstract fun bindSignInUseCase(impl: SignInUseCaseImpl): SignInUseCase

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(impl: UserDataRemoteDataSourceImpl): UserDataRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserDataRepository(impl: UserDataRepositoryImpl): UserDataRepository

    @Binds
    abstract fun bindGetUserDataUseCase(impl: GetUserDataUseCaseImpl): GetUserDataUseCase

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseFirestore() = Firebase.firestore
    }
}
