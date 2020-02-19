package com.hsiehavey.homeassistant

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TaskViewModel(application: Application): AndroidViewModel(application){

    var parentJob = Job()

    private val repository:TaskRepository

    val allTasks: LiveData<List<TaskData>>

    private var taskId = MutableLiveData<Int>()

    fun setId(input:Int){
        taskId.value = input
    }
    fun getId(): LiveData<Int>{
        return taskId
    }

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    var scope = CoroutineScope(coroutineContext)

    init {
        val taskDao = TaskRoomDatabase.getDatabase(application, scope).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insert(task:TaskData) = scope.launch(Dispatchers.IO){
        repository.insert(task)
        Log.d("VM", "insert called")
    }

    fun update(task:TaskData) = scope.launch(Dispatchers.IO){
        repository.updateTask(task)
        Log.d("VM", "update called")
    }
    fun delete(id:Int) = scope.launch(Dispatchers.IO){
        repository.delete(id)
        Log.d("VM", "delete called")
    }

    fun deleteAll() = scope.launch(Dispatchers.IO){
        repository.deleteAll()
        Log.d("VM", "deleteAll called")
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
        Log.d("VM", "onCleared called")
    }
}