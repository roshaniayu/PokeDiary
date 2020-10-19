package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.MainActivity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.PokemonViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.utils.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonBoxFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonBoxFragment : Fragment() {
    private lateinit var pokemonTextView: TextView
    private lateinit var viewModel: PokemonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_box, container, false)
        val factory = InjectorUtils.providePokemonViewModelFactory(itemView.context)
        viewModel = ViewModelProviders.of(this, factory).get(PokemonViewModel::class.java)

        (activity as MainActivity).toolbar.title = getString(R.string.app_name)
        pokemonTextView = itemView.findViewById(R.id.pokemon_box)

        initializeUi()

        return itemView
    }

    private fun initializeUi() {
        viewModel.getAllPokemon().observe(this, Observer { pokemonBox ->
            val stringBuilder = StringBuilder()
            if (pokemonBox.isNotEmpty()) {
                pokemonBox.forEach { pokemon ->
                    stringBuilder.append("${pokemon.name}\n\n")
                }
            } else {
                stringBuilder.append("There's no input yet")
            }

            pokemonTextView.text = stringBuilder.toString()
        })
    }
}