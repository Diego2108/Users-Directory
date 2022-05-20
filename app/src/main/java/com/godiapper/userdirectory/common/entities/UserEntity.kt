package com.godiapper.userdirectory.common.entities

import android.provider.ContactsContract
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity")
data class UserEntity(@PrimaryKey(autoGenerate = true)
                      var id:Long = 0,
                      var name: String,
                      var lastname: String,
                      var secondlastname: String,
                      var edad: String,
                      var phone: String){

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity

        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
