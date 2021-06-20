package com.ryan.myapplication.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todo_table")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val data: String
    ): Parcelable {
    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass) {
            return false
        }
        other as ToDo

        if(id != other.id) return false

        if(title != other.title) return false

        if(data != other.data) return false

        return true
    }
}