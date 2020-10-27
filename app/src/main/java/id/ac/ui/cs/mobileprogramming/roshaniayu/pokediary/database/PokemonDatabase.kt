package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao.PokeballDao
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao.PokemonDao
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokeballEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokemonEntity

@Database(entities = [PokemonEntity::class, PokeballEntity::class], version = 2, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun pokeballDao(): PokeballDao

    companion object {
        @Volatile
        private var instance: PokemonDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                "pokemon-database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}