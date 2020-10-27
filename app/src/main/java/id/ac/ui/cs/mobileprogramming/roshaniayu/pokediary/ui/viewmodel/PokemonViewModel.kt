package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokeballEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokemonEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository.PokemonRepository
import kotlinx.coroutines.ObsoleteCoroutinesApi

class PokemonViewModel (private val pokemonRepository: PokemonRepository)
    : ViewModel() {
    private val allPokemon: LiveData<List<PokemonEntity>> = pokemonRepository.getAllPokemon()

    @ObsoleteCoroutinesApi
    fun catchPokemon(pokemon: PokemonEntity) {
        pokemonRepository.insertPokemon(pokemon)
    }

    @ObsoleteCoroutinesApi
    fun addPokeball(pokeball: PokeballEntity) {
        pokemonRepository.insertPokeball(pokeball)
    }

    @ObsoleteCoroutinesApi
    fun updatePokemon(pokemon: PokemonEntity) {
        pokemonRepository.updatePokemon(pokemon)
    }

    @ObsoleteCoroutinesApi
    fun deletePokemon(pokemon: PokemonEntity) {
        pokemonRepository.deletePokemon(pokemon)
    }

    fun getAllPokemon(): LiveData<List<PokemonEntity>> {
        return allPokemon
    }
}