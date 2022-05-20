package com.godiapper.userdirectory.editModule.model

import com.godiapper.userdirectory.common.UserApplication
import com.godiapper.userdirectory.common.entities.UserEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.security.auth.callback.Callback

class EditUserIterator {
    fun saveUser(userEntity:UserEntity,callback: (Long) -> Unit){
        doAsync {
            val newId = UserApplication.database.userDao().addUsers(userEntity)
            UserApplication.database.userDao().deleteUsers(userEntity)
            uiThread {
                callback(newId)
            }
        }
    }

    fun updateUser(userEntity: UserEntity, callback: (UserEntity) -> Unit){
        doAsync {
            UserApplication.database.userDao().updateUsers(userEntity)
            uiThread {
                callback(userEntity)
            }
        }
    }
}