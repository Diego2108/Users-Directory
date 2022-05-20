package com.godiapper.userdirectory.common.utils

import com.godiapper.userdirectory.common.entities.UserEntity

interface MainAux {
    fun hideFab(isVisible: Boolean = false)

    fun addStore(userEntity: UserEntity)
    fun updateStore(userEntity: UserEntity)
}