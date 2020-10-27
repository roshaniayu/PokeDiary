package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity (
    @PrimaryKey @NonNull @ColumnInfo(name = "num") var num: String,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "img") var img: String?,
    @ColumnInfo(name = "height") var height: String?,
    @ColumnInfo(name = "weight") var weight: String?,
    @ColumnInfo(name = "level") var level: Int = 1)