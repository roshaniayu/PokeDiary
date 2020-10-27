package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokeballEntity

@Dao
interface PokeballDao {
    @Insert(onConflict = REPLACE)
    fun insert(pokeball: PokeballEntity)
}
