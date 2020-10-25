package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
data class DiaryEntity(
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "date") var date: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0)