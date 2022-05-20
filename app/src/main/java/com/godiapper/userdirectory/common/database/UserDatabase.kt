package com.godiapper.userdirectory.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.godiapper.userdirectory.common.entities.UserEntity

@Database(entities = arrayOf(UserEntity::class), version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}