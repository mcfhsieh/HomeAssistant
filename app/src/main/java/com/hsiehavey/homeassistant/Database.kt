package com.hsiehavey.homeassistant

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TaskData::class], version = 2,exportSchema = false)
abstract class TaskRoomDatabase : RoomDatabase(){

    abstract fun taskDao(): TaskDao

    companion object{
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope)
        : TaskRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance: TaskRoomDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    "Task_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(TaskDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
        private class TaskDatabaseCallback(
            private val scope: CoroutineScope
        ): RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let{database ->
                    scope.launch(Dispatchers.IO){
                        populateDatabase(database.taskDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(taskDao: TaskDao){
            taskDao.deleteAll()

            var task =  TaskData(0, "First Task", "Simple")
            taskDao.insert(task)
        }
    }
}