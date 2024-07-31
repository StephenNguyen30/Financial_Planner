package com.example.financialplanner.ui.theme.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.financialplanner.ui.theme.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}