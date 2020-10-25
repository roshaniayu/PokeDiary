package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.utils

import android.content.Context
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.DiaryDatabase
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.PokemonDatabase
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository.DiaryRepository
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository.PokemonRepository
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.factory.DiaryViewModelFactory
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.factory.PokemonViewModelFactory

object InjectorUtils {
    fun providePokemonViewModelFactory(context: Context): PokemonViewModelFactory {
        val contactsRepository = PokemonRepository.getInstance(PokemonDatabase.getInstance(context).pokemonDao())

        return PokemonViewModelFactory(
            contactsRepository
        )
    }

    fun provideDiaryViewModelFactory(context: Context): DiaryViewModelFactory {
        val contactsRepository = DiaryRepository.getInstance(DiaryDatabase.getInstance(context).diaryDao())

        return DiaryViewModelFactory(
            contactsRepository
        )
    }
}