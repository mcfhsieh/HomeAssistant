package com.hsiehavey.homeassistant.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hsiehavey.homeassistant.R
import com.hsiehavey.homeassistant.TaskData
import com.hsiehavey.homeassistant.TaskViewModel
import com.hsiehavey.homeassistant.databinding.NewTaskFragmentBinding
import com.hsiehavey.homeassistant.showKeyboard
import kotlinx.android.synthetic.main.new_task_fragment.*

class NewTaskFragment: BottomSheetDialogFragment(){

    var taskText:String = ""
    lateinit var binding:NewTaskFragmentBinding
    lateinit var newTaskButton: Button
    lateinit var behavior:BottomSheetBehavior<View>
    lateinit var dialog:BottomSheetDialog
    lateinit var viewModel:TaskViewModel

    companion object{
        fun newInstance() = NewTaskFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        viewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)
        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val sheet = d.bottom_sheet
            behavior = BottomSheetBehavior.from(sheet)
            behavior.isHideable = false
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            var editText = binding.newTaskText
            editText.showKeyboard()
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_task_fragment, container, false)
        taskText = binding.newTaskText.text.toString()
        newTaskButton = binding.taskSubmitButton
        newTaskButton.setOnClickListener {
            insertNewTask()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun insertNewTask(){
        var newTaskContent = binding.newTaskText.text.toString()
        if (newTaskContent.isBlank()){
            Toast.makeText(requireContext(), "Enter Text", Toast.LENGTH_LONG).show()
        }else{
            var newTask = TaskData(0, newTaskContent, "Simple")
            viewModel.insert(newTask)
            dialog.dismiss()
            binding.newTaskText.setText("", TextView.BufferType.EDITABLE)
        }

    }

    override fun onStop() {
        super.onStop()
    }

}
