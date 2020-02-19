package com.hsiehavey.homeassistant

import androidx.recyclerview.widget.DiffUtil
import androidx.room.*


@Entity(tableName = "tasks_table")
data class TaskData(@PrimaryKey(autoGenerate = true)
                    val id: Int = 0,
                    @ColumnInfo(name = "task_text") var taskText:String,
                    @ColumnInfo(name = "task_type") var taskType:String){




}
