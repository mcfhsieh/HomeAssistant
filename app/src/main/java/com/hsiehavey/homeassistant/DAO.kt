package com.hsiehavey.homeassistant

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao{

    @Query("SELECT * from tasks_table ORDER BY task_text ASC")
    fun getAllTasks(): LiveData<List<TaskData>>

    @Query("SELECT * from tasks_table WHERE id = :key")
    suspend fun getTask(key:Int): TaskData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task:TaskData)

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAll()

    @Query("DELETE FROM tasks_table WHERE id = :key")
    suspend fun delete(key:Int)

    @Update
    suspend fun updateTask(task: TaskData)

}