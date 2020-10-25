package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getAll(): LiveData<List<@JvmSuppressWildcards PokemonEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(pokemon: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonEntity)

    @Update
    fun update(pokemon: PokemonEntity)
}
