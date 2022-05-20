package com.godiapper.userdirectory.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.godiapper.userdirectory.common.entities.UserEntity
import com.godiapper.userdirectory.mainModule.model.MainIterator

class MainViewModel: ViewModel() {
    private var usersList: MutableList<UserEntity>
    private  var iterator: MainIterator

    init {
        usersList = mutableListOf()
        iterator = MainIterator()
    }

    private val users: MutableLiveData<List<UserEntity>> by lazy {
        MutableLiveData<List<UserEntity>>(). also {
            loadUsers()
        }
    }

    private fun loadUsers() {
        iterator.getUsers {
            users.value = it
            usersList = it
        }
    }

    fun getUsers(): LiveData<List<UserEntity>>{
        return users
    }

    fun deleteUsers(userEntity: UserEntity){
        iterator.deleteUsers(userEntity, {
            val index = usersList.indexOf(userEntity)
            if (index != -1){
                usersList.removeAt(index)
                users.value = usersList
            }
        })
    }

    fun updateUsers(userEntity: UserEntity){
        iterator.updateUsers(userEntity,{
            val index = usersList.indexOf(userEntity)
            if (index != -1){
                usersList.set(index, userEntity)
                users.value = usersList
            }
        })
    }
}