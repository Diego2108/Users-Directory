package com.godiapper.userdirectory.mainModule.model

import com.godiapper.userdirectory.common.UserApplication
import com.godiapper.userdirectory.common.entities.UserEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainIterator {
    fun getUsers(callback: (MutableList<UserEntity>) -> Unit){
        doAsync {
            val storeList = UserApplication.database.userDao().getAllUsers()
            uiThread {
                callback(storeList)
            }
        }
    }

    fun deleteUsers(storeEntity: UserEntity, callback: (UserEntity) -> Unit){
        doAsync {
            UserApplication.database.userDao().deleteUsers(storeEntity)
            uiThread {
                callback(storeEntity)
            }
        }
    }

    fun updateUsers(storeEntity: UserEntity, callback: (UserEntity) -> Unit){
        doAsync {
            UserApplication.database.userDao().updateUsers(storeEntity)
            uiThread {
                callback(storeEntity)
            }
        }
    }
}