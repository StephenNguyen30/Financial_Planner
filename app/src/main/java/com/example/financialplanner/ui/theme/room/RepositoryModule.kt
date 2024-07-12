package com.example.financialplanner.ui.theme.room

import com.example.financialplanner.ui.theme.implementation.TransactionRepositoryImpl
import com.example.financialplanner.ui.theme.implementation.UserRepositoryImpl
import com.example.financialplanner.ui.theme.respository.TransactionRepository
import com.example.financialplanner.ui.theme.respository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun provideMatchRepository(bind: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun provideTransactionRepository(bind: TransactionRepositoryImpl): TransactionRepository
}