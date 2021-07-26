package com.jimin.memoria.data.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.jimin.memoria.data.entites.User

@Database(entities = [User::class], version = 1)
abstract class UserDB : RoomDatabase(){
    abstract fun userDao(): UserDao
}