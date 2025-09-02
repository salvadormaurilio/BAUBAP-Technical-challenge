package com.example.baubapchallenge.di

import com.example.baubapchallenge.data.datasource.AuthRemoteDataSource
import com.example.baubapchallenge.data.datasource.AuthRemoteDataSourceImpl
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
    abstract fun bindCountriesRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseFirestore() = Firebase.firestore
    }
}
