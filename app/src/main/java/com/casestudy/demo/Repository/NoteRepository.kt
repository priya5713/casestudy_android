package com.casestudy.demo.Repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import com.casestudy.demo.model.Note
import com.casestudy.demo.util.AppUtils

class NoteRepository(context: Context) {

    private val DB_NAME = "db_task"

    private val noteDatabase: NoteDatabase

    val tasks: LiveData<List<Note>>
        get() = noteDatabase.daoAccess().fetchAllTasks()

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }

    @JvmOverloads
    fun insertTask(title: String,
                   description: String,
                   encrypt: Boolean = false,
                   password: String? = null) {

        val note = Note()
        note.title = title
        note.description = description
        note.createdAt = AppUtils.currentDateTime
        note.modifiedAt = AppUtils.currentDateTime
        note.isEncrypt = encrypt


        if (encrypt) {
            note.password = AppUtils.generateHash(password.toString())
        } else
            note.password = null

        insertTask(note)
    }

    fun insertTask(note: Note) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                noteDatabase.daoAccess().insertTask(note)
                return null
            }
        }.execute()
    }

    fun updateTask(note: Note) {
        note.modifiedAt = AppUtils.currentDateTime

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                noteDatabase.daoAccess().updateTask(note)
                return null
            }
        }.execute()
    }

    fun deleteTask(id: Int) {
        val task = getTask(id)
        if (task != null) {
            object : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg voids: Void): Void? {
                    noteDatabase.daoAccess().deleteTask(task.value!!)
                    return null
                }
            }.execute()
        }
    }

    fun deleteTask(note: Note) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                noteDatabase.daoAccess().deleteTask(note)
                return null
            }
        }.execute()
    }

    fun getTask(id: Int): LiveData<Note> {
        return noteDatabase.daoAccess().getTask(id)
    }
}

