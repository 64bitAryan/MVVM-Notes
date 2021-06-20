package com.ryan.myapplication

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ryan.myapplication.R
import com.ryan.myapplication.adapter.TodoAdapter
import com.ryan.myapplication.adapter.TodoItemClicked
import com.ryan.myapplication.db.ToDo
import com.ryan.myapplication.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class listFragment : Fragment(), TodoItemClicked, SearchView.OnQueryTextListener {

    private lateinit var  mTodoViewModel: TodoViewModel
    private lateinit var mTodoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        // RecyclerView
        mTodoAdapter = TodoAdapter(this)
        val recyclerview = view.recyclerView
        recyclerview.adapter = mTodoAdapter
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        recyclerview.layoutManager = staggeredGridLayoutManager

        //Setting up ViewModel
        mTodoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        mTodoViewModel.allTodos.observe(viewLifecycleOwner, Observer {
            mTodoAdapter.setData(it)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Setting Up SearchBox
        val searchView = view.search_et
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return view
    }

    override fun onItemClicked(item: ToDo) {
        mTodoViewModel.deleteTodo(item)
        Toast.makeText(requireContext(),"Task Deleted !", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        mTodoViewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let{
                mTodoAdapter.setData(it)
            }
        })
    }
}