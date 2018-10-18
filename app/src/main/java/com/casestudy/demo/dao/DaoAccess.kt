package com.casestudy.demo.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.casestudy.demo.model.Note

@Dao
interface DaoAccess {

    @Insert
    fun insertTask(note: Note): Long


    @Query("SELECT * FROM Note ORDER BY created_at desc")
    fun fetchAllTasks(): LiveData<List<Note>>


    @Query("SELECT * FROM Note WHERE id =:taskId")
    fun getTask(taskId: Int): LiveData<Note>


    @Update
    fun updateTask(note: Note)


    @Delete
    fun deleteTask(note: Note)
}
