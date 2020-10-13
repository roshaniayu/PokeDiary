package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common

import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.model.Pokemon

object Common {
    var pokemonList: List<Pokemon> = ArrayList()
    const val KEY_ENABLE_HOME = "position"

    fun findPokemonByNum(num: String): Pokemon? {
        for (pokemon in Common.pokemonList)
            if (pokemon.num.equals(num)) {
                return pokemon
            }

        return null
    }
}