package com.ryan.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ryan.myapplication.R
import com.ryan.myapplication.db.ToDo
import com.ryan.myapplication.listFragmentDirections
import kotlinx.android.synthetic.main.recycler_item.view.*

class TodoAdapter(val listener: TodoItemClicked): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    var todoList = emptyList<ToDo>()

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleEt: TextView = itemView.findViewById(R.id.title_item)
        val textEt: TextView = itemView.findViewById(R.id.text_item)
        val cardView: CardView = itemView.findViewById(R.id.card_view)
    }

    class TodoItemDiffCallBack(
        var oldTodoList: List<ToDo>,
        var newTodoList: List<ToDo>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldTodoList.size
        }

        override fun getNewListSize(): Int {
            return newTodoList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldTodoList.get(oldItemPosition).id == newTodoList.get(newItemPosition).id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldTodoList.get(oldItemPosition).equals(newTodoList.get(newItemPosition))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = todoList[position]
        holder.titleEt.text = current.title
        holder.textEt.text = current.data
        holder.cardView.setOnClickListener {
            val action = listFragmentDirections.actionListFragmentToUpdateFragment(current)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.delete_todo.setOnClickListener{
            listener.onItemClicked(current)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setData(todo: List<ToDo>) {
        val oldList = todoList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            TodoItemDiffCallBack(
                oldList,
                todo
            )
        )
        todoList = todo
        diffResult.dispatchUpdatesTo(this)
    }
}
interface TodoItemClicked{
    fun onItemClicked(item: ToDo)
}