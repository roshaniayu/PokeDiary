package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.network

import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.model.PokemonResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface PokedexService {
    @get:GET("pokedex.json")
    val pokemonList: Observable<PokemonResponse>
}