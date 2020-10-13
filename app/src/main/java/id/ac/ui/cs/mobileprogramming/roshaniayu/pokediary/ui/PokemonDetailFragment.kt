package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        return itemView
    }

    companion object {
        internal var instance: PokemonDetailFragment?=null

        fun getInstance() : PokemonDetailFragment {
            if (instance == null) {
                instance = PokemonDetailFragment()
            }

            return instance as PokemonDetailFragment
        }
    }
}