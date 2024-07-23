package com.example.financialplanner.ui.theme.mapper

import com.example.financialplanner.ui.theme.model.UserModel
import com.example.financialplanner.ui.theme.entity.UserEntity

fun UserEntity.mapToUserModel() = UserModel(
    id = id,
    imageUrl = imageUrl,
)

fun UserModel.mapToUserEntity() = UserEntity(
    id = id,
    imageUrl = imageUrl,
    firstName = firstName,
    familyName = lastName,
    phoneNumber = phoneNumber,
)