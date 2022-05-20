package com.godiapper.userdirectory.mainModule.adapter

import com.godiapper.userdirectory.common.entities.UserEntity

interface OnclickListener {
    fun onClick(userEntity: UserEntity)
    fun onDeleteUser(userEntity: UserEntity)
}