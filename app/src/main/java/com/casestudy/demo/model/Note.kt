package com.casestudy.demo.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters

import com.casestudy.demo.util.TimestampConverter

import java.io.Serializable
import java.util.Date


@Entity
class Note : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var title: String? = null
    var description: String? = null


    @ColumnInfo(name = "created_at")
    @TypeConverters(TimestampConverter::class)
    var createdAt: Date? = null

    @ColumnInfo(name = "modified_at")
    @TypeConverters(TimestampConverter::class)
    var modifiedAt: Date? = null

    var isEncrypt: Boolean = false
    var password: String? = null
}
