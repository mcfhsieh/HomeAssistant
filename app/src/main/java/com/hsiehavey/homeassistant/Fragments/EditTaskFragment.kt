package com.hsiehavey.homeassistant.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.hsiehavey.homeassistant.R
import com.hsiehavey.homeassistant.TaskData
import com.hsiehavey.homeassistant.TaskViewModel
import com.hsiehavey.homeassistant.databinding.FragmentEditTaskBinding

class EditTaskFragment : Fragment() {

    lateinit var viewModel: TaskViewModel
    lateinit var taskText:String
    var taskId = 0


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
        val args = arguments?.let {
            var safeArgs = EditTaskFragmentArgs.fromBundle(it)
            taskId = safeArgs.taskId
        }
        viewModel.allTasks.observe(viewLifecycleOwner, Observer { it ->
            it.let {
                it.forEach{
                    if (it.id == taskId){
                        taskText = it.taskText
                    }
                }
            }

        })



        val editText = binding.taskEditInput
        editText.setText(taskText)

        return binding.root

    }

}
