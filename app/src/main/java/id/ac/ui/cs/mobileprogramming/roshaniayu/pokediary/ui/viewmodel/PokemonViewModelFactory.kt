package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository.PokemonRepository

class PokemonViewModelFactory (private val pokemonRepository: PokemonRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return PokemonViewModel(pokemonRepository) as T
    }
}