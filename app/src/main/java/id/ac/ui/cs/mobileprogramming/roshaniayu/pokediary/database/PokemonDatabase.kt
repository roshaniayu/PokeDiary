package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity::class], version = 2, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

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