package com.ryan.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: ToDo)

    @Delete
    suspend fun deleteTodo(todo: ToDo)

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTodos(): LiveData<List<ToDo>>

    @Update
    suspend fun updateTodo(todo: ToDo)

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR data Like :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDo>>
}