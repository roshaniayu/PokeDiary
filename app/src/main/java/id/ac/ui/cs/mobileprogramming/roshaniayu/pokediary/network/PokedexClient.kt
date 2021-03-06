package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object PokedexClient {
    private var pokedexInstance: Retrofit? = null
    val instance: Retrofit

    get() {
        if (pokedexInstance == null)
            pokedexInstance = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return pokedexInstance!!
    }
}