package com.jimin.memoria.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jimin.memoria.data.entites.Memo

@Database(entities = [Memo::class], version = 1)
abstract class MemoDB : RoomDatabase() {
    abstract fun memoDao():MemoDao
}