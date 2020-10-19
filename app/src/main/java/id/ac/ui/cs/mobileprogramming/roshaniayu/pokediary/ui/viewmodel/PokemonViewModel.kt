package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.PokemonEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository.PokemonRepository

class PokemonViewModel (private val pokemonRepository: PokemonRepository)
    : ViewModel() {
    private val allPokemon: LiveData<List<PokemonEntity>> = pokemonRepository.getAllPokemon()

    @kotlinx.coroutines.ObsoleteCoroutinesApi
    fun catchPokemon(pokemon: PokemonEntity) {
        pokemonRepository.insertPokemon(pokemon)
    }

    @kotlinx.coroutines.ObsoleteCoroutinesApi
    fun updatePokemon(pokemon: PokemonEntity) {
        pokemonRepository.updatePokemon(pokemon)
    }

    @kotlinx.coroutines.ObsoleteCoroutinesApi
    fun deletePokemon(pokemon: PokemonEntity) {
        pokemonRepository.deletePokemon(pokemon)
    }

    fun getAllPokemon(): LiveData<List<PokemonEntity>> {
        return allPokemon
    }
}