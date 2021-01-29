package com.hsiehavey.homeassistant.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hsiehavey.homeassistant.*
import com.hsiehavey.homeassistant.R
import com.hsiehavey.homeassistant.databinding.TaskListFragmentBinding
import com.hsiehavey.homeassistant.databinding.TaskListViewBinding
import kotlinx.android.synthetic.main.task_list_view.view.*


class TaskViewFragment : Fragment() {

    private lateinit var viewModel:TaskViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:TaskAdapter
    private var newTaskDialog: NewTaskFragment? = null
    private var newDeleteTaskDialog: DeleteTaskDialogFragment? = null
    private var longPress = false
    private lateinit var adapterBinding:TaskListViewBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FRAG", "onCreateView called")

        val binding: TaskListFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.task_list_fragment,container,false)

        adapterBinding = DataBindingUtil.inflate(inflater,R.layout.task_list_view, container, false)


        val newTaskButton:FloatingActionButton = binding.newTaskButton

        binding.bottomAppBar

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        adapter = TaskAdapter(TaskDataListener { taskId ->
            Toast.makeText(requireActivity(), "Task Clicked: $taskId",Toast.LENGTH_LONG).show()
            val bundle = bundleOf("Task ID" to taskId)
            if (!longPress){
                view?.findNavController()?.navigate(R.id.action_taskViewFragment_to_editTaskFragment, bundle)
            }
            else {
                println(taskId)
            }
        }).apply {
            onItemLongPress = {taskData ->
                Toast.makeText(requireContext(), taskData.taskText, Toast.LENGTH_LONG).show()
                if (newDeleteTaskDialog == null){
                    newDeleteTaskDialog = DeleteTaskDialogFragment(taskData)
                    newDeleteTaskDialog?.let { dialog ->
                        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                    }
                }
                else newDeleteTaskDialog?.dialog?.show()

            }
        }
        setRecyclerView(binding)

        newTaskButton.setOnClickListener {
            if (newTaskDialog == null) {
                newTaskDialog = NewTaskFragment.newInstance()
                newTaskDialog?.let { dialog ->
                    dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                }
            }
            else newTaskDialog?.dialog?.show()

        }
        return binding.root
    }

    private fun setRecyclerView(binding:TaskListFragmentBinding) {
        recyclerView = binding.mRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.allTasks.observe(viewLifecycleOwner, Observer { it ->
            it.let {
                adapter.submitList(it)
                Log.d("FRAG", "Tasks updated")
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("FRAG", "onActivityCreate")
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("FRAG", "onAttach")
    }

    override fun onDetach(){
        super.onDetach()
        Log.d("FRAG", "onDetach ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FRAG", "onResume")
    }




}