package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.utils

import android.content.Context
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.PokemonDatabase
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository.PokemonRepository
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.PokemonViewModelFactory

object InjectorUtils {
    fun providePokemonViewModelFactory(context: Context): PokemonViewModelFactory {
        val contactsRepository = PokemonRepository.getInstance(PokemonDatabase.getInstance(context).pokemonDao())

        return PokemonViewModelFactory(contactsRepository)
    }
}