package com.hsiehavey.homeassistant.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hsiehavey.homeassistant.R
import com.hsiehavey.homeassistant.TaskData
import com.hsiehavey.homeassistant.TaskViewModel
import com.hsiehavey.homeassistant.databinding.FragmentEditTaskBinding

class EditTaskFragment : Fragment() {

    lateinit var viewModel: TaskViewModel
    lateinit var newTaskText:String
    lateinit var newTaskType:String
    var taskText = ""
    var taskId = 0
    lateinit var editText:EditText
    lateinit var draftText:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditTaskBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_task,container, false)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.viewModel = viewModel

        editText = binding.taskEditInput

        var appBar = binding.topAppBar

        appBar.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.delete_button ->{
                    Log.d("delete", "delete item clicked")
                    if (taskId>0){
                        view?.findNavController()?.navigate(R.id.action_editTaskFragment_to_taskViewFragment)
                        viewModel.delete(taskId)

                    }else println(taskId)
                }
                R.id.accept_button ->{
                    Log.d("changes accepted", "accept item clicked")
                    if (taskId>0){
                        view?.findNavController()?.navigate(R.id.action_editTaskFragment_to_taskViewFragment)
                        //viewModel.update(taskId)
                        updateTaskText()

                    }else println(taskId)
                }
                else -> false
            }
            true
        }


        viewModel.allTasks.observe(viewLifecycleOwner, Observer { it ->
            it.forEach {
                    if (it.id == taskId){
                        newTaskType = it.taskType
                        editText.setText(it.taskText)
                        draftText = it.taskText
                }
            }

        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        var args = arguments

        if (args != null){
            taskId = args.getInt("Task ID")
            Log.d("EditTask", "Task ID = $taskId")
        }
    }

    override fun onPause() {
        super.onPause()

/*        var currentText = editText.text.toString()
        if(currentText != draftText){
            var taskUpdate = TaskData(taskId,currentText, newTaskType)
            viewModel.update(taskUpdate)
        }*/
        Log.d("FRAG", "onPause() called")
    }

    fun updateTaskText(){
        var currentText = editText.text.toString()
        if(currentText != draftText){
            var taskUpdate = TaskData(taskId,currentText, newTaskType)
            viewModel.update(taskUpdate)
        }
    }
}
