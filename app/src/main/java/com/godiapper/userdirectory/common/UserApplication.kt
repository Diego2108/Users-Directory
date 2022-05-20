package com.godiapper.userdirectory.common

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.godiapper.userdirectory.common.database.UserDatabase

class UserApplication: Application() {

    companion object{
        lateinit var database: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE UserEntity ADD COLUMN photoUrl TEXT NOT NULL DEFAULT''")
            }
        }

        database = Room.databaseBuilder(this,
            UserDatabase::class.java,
            "UserDatabase")
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}