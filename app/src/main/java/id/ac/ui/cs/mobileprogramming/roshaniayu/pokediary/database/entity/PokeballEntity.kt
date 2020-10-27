package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity

import androidx.room.*

@Entity(tableName = "pokeball",
    foreignKeys = [ForeignKey(entity = PokemonEntity::class,
        parentColumns = arrayOf("num"),
        childColumns = arrayOf("pokemon_num"),
        onDelete = ForeignKey.CASCADE)])
data class PokeballEntity(
    @ColumnInfo(name = "pokemon_num") var num: String?,
    @ColumnInfo(name = "pokemon_name") var pokemon_name: String?,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0)
