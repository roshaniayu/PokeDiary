package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokeball")
    fun getAll(): LiveData<List<@JvmSuppressWildcards PokemonEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(pokemon: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonEntity)

    @Update
    fun update(pokemon: PokemonEntity)
}
