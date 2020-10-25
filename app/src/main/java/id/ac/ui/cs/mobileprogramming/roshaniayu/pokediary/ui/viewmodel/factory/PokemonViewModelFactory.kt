package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository.PokemonRepository
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.PokemonViewModel

class PokemonViewModelFactory (private val pokemonRepository: PokemonRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return PokemonViewModel(
            pokemonRepository
        ) as T
    }
}