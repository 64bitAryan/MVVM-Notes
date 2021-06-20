package com.ryan.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ryan.myapplication.db.ToDo
import com.ryan.myapplication.db.TodoDatabase
import com.ryan.myapplication.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {
    val allTodos: LiveData<List<ToDo>>
    private val repository: TodoRepository
    init {
        val todoDao = TodoDatabase.getDatabase(application).getTodoDao()
        repository = TodoRepository(todoDao)
        allTodos = repository.allTodo
    }

    fun addTodo(todo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo)
        }
    }

    fun updateUser(todo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDo>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}