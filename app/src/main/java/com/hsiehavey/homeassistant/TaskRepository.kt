package com.hsiehavey.homeassistant

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao){

    val allTasks: LiveData<List<TaskData>> = taskDao.getAllTasks()



    suspend fun insert(task:TaskData){
        taskDao.insert(task)
    }

    suspend fun getTask(id: Int): TaskData {
         return taskDao.getTask(id)
    }

    suspend fun deleteAll(){
        taskDao.deleteAll()
    }

    suspend fun delete(id:Int){
        taskDao.delete(id)
    }
    suspend fun updateTask(task: TaskData){
        taskDao.updateTask(task)
    }
}