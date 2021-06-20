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
import androidx.navigation.fragment.navArgs
import com.ryan.myapplication.db.ToDo
import com.ryan.myapplication.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class updateFragment : Fragment() {

    private val args by navArgs<updateFragmentArgs>()
    private lateinit var mTodoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        mTodoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        view.update_title_et.setText(args.currentTodo.title)
        view.update_text_et.setText(args.currentTodo.data)

        view.update_save_btn.setOnClickListener {
            updateItem()
        }

        return view
    }

    private fun updateItem() {
        val title = update_title_et.text.toString()
        val text = update_text_et.text.toString()
        if(inputCheck(title, text)) {
            //Create todo Object
            val updateTodo = ToDo(args.currentTodo.id,title, text)

            //Update Current todo
            mTodoViewModel.updateUser(updateTodo)

            //Navigate Back to main screen
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "Updated Successfully !", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Cant Update \nFields are empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, text: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(text))
    }
}