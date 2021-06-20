package com.ryan.myapplication

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ryan.myapplication.R
import com.ryan.myapplication.db.ToDo
import com.ryan.myapplication.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class addFragment : Fragment() {

    private lateinit var mTodoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mTodoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        view.save_btn.setOnClickListener {
            insertDataIntoDatabase()
        }

        return view
    }

    private fun insertDataIntoDatabase() {
        val title = title_et.text.toString()
        val text = text_et.text.toString()

        if(inputCheck(title, text)) {
            val todo = ToDo(0,title, text)
            mTodoViewModel.addTodo(todo)
            Toast.makeText(requireContext(), "Todo Added !", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Title or Text Field is empty...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String, text: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(text))
    }
}