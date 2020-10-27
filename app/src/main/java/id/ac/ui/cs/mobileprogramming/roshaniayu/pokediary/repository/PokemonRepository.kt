package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao.PokeballDao
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao.PokemonDao
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokeballEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokemonEntity
import kotlinx.coroutines.*

class PokemonRepository private constructor(private val pokemonDao: PokemonDao, private val pokeballDao: PokeballDao) {
    private val allPokemon: LiveData<List<PokemonEntity>> = pokemonDao.getAll()

    @ObsoleteCoroutinesApi
    var thread = newSingleThreadContext("pokemonRepository") as CoroutineDispatcher

    @ObsoleteCoroutinesApi
    fun insertPokemon(pokemon: PokemonEntity) = GlobalScope.launch(thread) {
        pokemonDao.insert(pokemon)
    }

    @ObsoleteCoroutinesApi
    fun insertPokeball(pokeball: PokeballEntity) = GlobalScope.launch(thread) {
        pokeballDao.insert(pokeball)
    }

    fun getAllPokemon(): LiveData<List<PokemonEntity>> {
        return allPokemon
    }

    @ObsoleteCoroutinesApi
    fun updatePokemon(pokemon: PokemonEntity) = GlobalScope.launch(thread) {
        pokemonDao.update(pokemon)
    }

    @ObsoleteCoroutinesApi
    fun deletePokemon(pokemon: PokemonEntity) = GlobalScope.launch(thread) {
        pokemonDao.delete(pokemon)
    }

    companion object {
        @Volatile private var instance: PokemonRepository? = null

        fun getInstance(pokemonDao: PokemonDao, pokeballDao: PokeballDao) =
            instance ?: synchronized(this) {
                instance ?: PokemonRepository(pokemonDao, pokeballDao).also { instance = it }
            }
    }
}