package com.hsiehavey.homeassistant


import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hsiehavey.homeassistant.databinding.TaskListViewBinding
import com.hsiehavey.homeassistant.fragments.TaskViewFragment


class TaskAdapter(private val clickListener: TaskDataListener) :
    ListAdapter<TaskData, TaskAdapter.ViewHolder>(TaskDataDiffCallback()) {

    var onItemLongPress: ((TaskData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("Adapter", "onCreateViewHolder called")
        return ViewHolder.from(parent).apply {
            onItemLongPress = { taskData ->
                this@TaskAdapter.onItemLongPress?.invoke(taskData)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
        Log.d("Adapter", "onBindViewHolder called")
    }

    class ViewHolder private constructor(private val binding: TaskListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var onItemLongPress: ((TaskData) -> Unit)? = null

        fun bind(item: TaskData, clickListener: TaskDataListener) {
            Log.d("Adapter", "bind function called")
            binding.tasks = item
            binding.taskTextView.text = item.taskText
            binding.taskTypeTextView.text = item.taskType
            binding.clickListener = clickListener
            binding.executePendingBindings()
            //handling longpress on recyclerview
            binding.cardView.setOnLongClickListener {
                onItemLongPress?.invoke(item)
                return@setOnLongClickListener true
            }
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskListViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)

            }
        }
    }
}

class TaskDataDiffCallback : DiffUtil.ItemCallback<TaskData>() {
    override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem == newItem
    }

}

class TaskDataListener(val clickListener: (taskId: Int) -> Unit) {
    fun onClick(task: TaskData) = clickListener(task.id)
}












/*class TaskAdapter: ListAdapter<TaskData, TaskViewHolder>(TaskDataDiffCallback()) {


    private var tasks = emptyList<TaskData>()
    var mClickListener: ((TaskData, View) -> Unit)? = null


    class TaskViewHolder private constructor(val binding:TaskItemViewBinding): TaskViewHolder(binding.root){
        fun bind(item:TaskData){
            binding.data = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent:ViewGroup):TaskViewholder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemViewBinding.inflate(layoutInflater,parent,false)
                return   TaskViewHolder(binding)
            }
        }
    }

    internal fun setTasks(tasks:List<TaskData>) {
        this.tasks = tasks
        notifyDataSetChanged()
        Log.d("Adapter", "Adapter Updated")
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.from(parent)
    }
*/



