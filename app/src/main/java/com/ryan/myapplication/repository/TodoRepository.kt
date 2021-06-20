package com.ryan.myapplication.repository

import androidx.lifecycle.LiveData
import com.ryan.myapplication.db.ToDo
import com.ryan.myapplication.db.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao: TodoDao) {
    val allTodo: LiveData<List<ToDo>> = dao.getAllTodos()

    suspend fun insertTodo(todo: ToDo) {
        dao.insertTodo(todo)
    }

    suspend fun updateTodo(todo: ToDo){
        dao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: ToDo) {
        dao.deleteTodo(todo)
    }

    fun searchDatabase(searchQuery: String): Flow<List<ToDo>> {
        return dao.searchDatabase(searchQuery)
    }
}