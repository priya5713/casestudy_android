package com.casestudy.demo.Repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.casestudy.demo.dao.DaoAccess
import com.casestudy.demo.model.Note


@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun daoAccess(): DaoAccess
}
