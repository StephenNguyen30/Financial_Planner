package com.example.financialplanner.ui.theme.implementation


import com.example.financialplanner.ui.theme.database.UserDao
import com.example.financialplanner.ui.theme.mapper.mapToUserEntity
import com.example.financialplanner.ui.theme.mapper.mapToUserModel
import com.example.financialplanner.ui.theme.model.UserModel
import com.example.financialplanner.ui.theme.respository.UserRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: UserModel) {
        val userMap = user.mapToUserEntity()
        userDao.insert(userMap)
    }

    override suspend fun getUserByIdToken(id: String) = userDao.getUserByIdToken(id).map {
        it.mapToUserModel()
    }

}
