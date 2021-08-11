package com.jimin.memoria.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "MemoTable")
data class Memo (@PrimaryKey(autoGenerate = true) var idx: Int=0,
    var title:String="",
    var datetime:String="",
    var content:String="",
    var isBookmark:String="N",
    var writer:Int=0
)

