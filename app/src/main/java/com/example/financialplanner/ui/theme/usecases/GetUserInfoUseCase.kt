package com.example.financialplanner.ui.theme.usecases

import com.example.financialplanner.ui.theme.base.IoDispatcher
import com.example.financialplanner.ui.theme.respository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserInfoUseCase
@Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,

) {
    suspend operator fun invoke(id: String) = withContext(ioDispatcher) {
        userRepository.getUserByIdToken(id)
    }
}