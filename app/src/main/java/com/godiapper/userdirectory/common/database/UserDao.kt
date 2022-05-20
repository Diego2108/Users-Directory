package com.godiapper.userdirectory.common.database

import androidx.room.*
import com.godiapper.userdirectory.common.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAllUsers(): MutableList<UserEntity>

    @Query("SELECT * FROM UserEntity where id = :id")
    fun getUserById(id:Long): UserEntity

    @Insert
    fun addUsers(userEntity: UserEntity): Long

    @Update
    fun updateUsers(userEntity: UserEntity)

    @Delete
    fun deleteUsers(userEntity: UserEntity)
}