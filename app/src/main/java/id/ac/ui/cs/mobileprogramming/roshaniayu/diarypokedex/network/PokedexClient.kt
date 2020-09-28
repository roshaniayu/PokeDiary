package id.ac.ui.cs.mobileprogramming.roshaniayu.diarypokedex.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object PokedexClient {
    private var pokedexService: Retrofit? = null

    val instance:Retrofit
    get() {
        if (pokedexService == null)
            pokedexService = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return pokedexService!!
    }
}