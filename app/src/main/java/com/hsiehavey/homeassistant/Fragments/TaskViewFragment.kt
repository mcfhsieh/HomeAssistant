package com.hsiehavey.homeassistant.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.navigation.NavController

import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hsiehavey.homeassistant.*
import com.hsiehavey.homeassistant.R
import com.hsiehavey.homeassistant.databinding.TaskListFragmentBinding


class TaskViewFragment : Fragment() {

    private lateinit var viewModel:TaskViewModel
    private lateinit var newTaskButton:FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FRAG", "onCreateView called")
        val binding:TaskListFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.task_list_fragment,container,false)

        val adapter = TaskAdapter(mylambda)

        binding.mRecyclerView.adapter = adapter

        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)

        newTaskButton = binding.newTaskButton
        newTaskButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_taskViewFragment_to_newTaskFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        viewModel.getId().observe(viewLifecycleOwner, Observer{

        })
        val mylambda = TaskDataListener{taskId ->
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("FRAG", "onAttach called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FRAG", "onDetach called")

    }

}