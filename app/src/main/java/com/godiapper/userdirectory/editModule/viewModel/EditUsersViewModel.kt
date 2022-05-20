package com.godiapper.userdirectory.editModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.godiapper.userdirectory.common.entities.UserEntity
import com.godiapper.userdirectory.editModule.model.EditUserIterator

class EditUsersViewModel:  ViewModel() {
    private val userSelected = MutableLiveData<UserEntity>()
    private val showFab = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()

    private val iterator: EditUserIterator

    init {
        iterator = EditUserIterator()
    }

    fun setUserSelected(userEntity: UserEntity){
        userSelected.value = userEntity
    }

    fun getUserSelected(): LiveData<UserEntity>{
        return userSelected
    }

    fun setShowFab(isVisible: Boolean){
        showFab.value = isVisible
    }

    fun getShowFab(): LiveData<Boolean>{
        return showFab
    }

    fun setResult(value: Any){
        result.value = value
    }

    fun getResult(): LiveData<Any>{
        return result
    }

    fun saveUser(userEntity: UserEntity){
        iterator.saveUser(userEntity, { newId ->
            result.value = newId
        })
    }

    fun updateUser(userEntity: UserEntity){
        iterator.updateUser(userEntity, { userUpdate ->
            result.value = userUpdate
        })
    }
}