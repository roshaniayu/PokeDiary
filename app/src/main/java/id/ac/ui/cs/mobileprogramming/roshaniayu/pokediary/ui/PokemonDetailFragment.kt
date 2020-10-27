package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.MainActivity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.PokemonEvolutionAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.PokemonTypeAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.Common
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokeballEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokemonEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.model.Pokemon
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.PokemonViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.utils.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ObsoleteCoroutinesApi

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonDetailFragment : Fragment() {
    private lateinit var pokemonImage: ImageView
    private lateinit var pokemonName: TextView
    private lateinit var pokemonHeight: TextView
    private lateinit var pokemonWeight: TextView
    private lateinit var prevEmptyView: TextView
    private lateinit var nextEmptyView: TextView
    private lateinit var catchPokemonButton: Button
    private lateinit var itemView: View
    private lateinit var viewModel: PokemonViewModel
    lateinit var recyclerType: RecyclerView
    lateinit var recyclerWeakness: RecyclerView
    lateinit var recyclerPrevEvolution : RecyclerView
    lateinit var recyclerNextEvolution: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
        val factory = InjectorUtils.providePokemonViewModelFactory(itemView.context)
        viewModel = ViewModelProviders.of(this, factory).get(PokemonViewModel::class.java)

        (activity as MainActivity).toolbar.title = getString(R.string.detail_name)
        val pokemon: Pokemon? = if (arguments?.getString("num") == null) {
            Common.pokemonList[arguments?.getInt("position")!!]
        } else {
            Common.findPokemonByNum(arguments?.getString("num")!!)
        }

        pokemonImage = itemView.findViewById(R.id.image)
        pokemonName = itemView.findViewById(R.id.name)
        pokemonHeight = itemView.findViewById(R.id.height)
        pokemonWeight = itemView.findViewById(R.id.weight)
        prevEmptyView = itemView.findViewById(R.id.prev_evolution_empty)
        nextEmptyView = itemView.findViewById(R.id.next_evolution_empty)
        catchPokemonButton = itemView.findViewById(R.id.catch_pokemon)

        recyclerType = itemView.findViewById(R.id.type_recyclerview)
        recyclerType.setHasFixedSize(true)
        recyclerType.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recyclerWeakness = itemView.findViewById(R.id.weakness_recyclerview)
        recyclerWeakness.setHasFixedSize(true)
        recyclerWeakness.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recyclerPrevEvolution = itemView.findViewById(R.id.prev_evolution_recyclerview)
        recyclerPrevEvolution.setHasFixedSize(true)
        recyclerPrevEvolution.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recyclerNextEvolution = itemView.findViewById(R.id.next_evolution_recyclerview)
        recyclerNextEvolution.setHasFixedSize(true)
        recyclerNextEvolution.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        setDetailPokemon(pokemon!!)

        return itemView
    }

    @ObsoleteCoroutinesApi
    private fun setDetailPokemon(pokemon: Pokemon) {
        // Load image
        Glide.with(activity!!).load(pokemon.img).into(pokemonImage)

        pokemonName.text = pokemon.name
        pokemonHeight.text = "Height: " + pokemon.height
        pokemonWeight.text = "Weight: " + pokemon.weight

        val typeAdapter = PokemonTypeAdapter(activity!!, pokemon.type!!)
        recyclerType.adapter = typeAdapter

        val weaknessAdapter = PokemonTypeAdapter(activity!!, pokemon.weaknesses!!)
        recyclerWeakness.adapter = weaknessAdapter

        if (pokemon.prev_evolution != null) {
            val prevEvolutionAdapterAdapter = PokemonEvolutionAdapter(activity!!, pokemon.prev_evolution!!)
            recyclerPrevEvolution.adapter = prevEvolutionAdapterAdapter
        } else {
            prevEmptyView.visibility = View.VISIBLE
            recyclerPrevEvolution.visibility = View.GONE
        }

        if (pokemon.next_evolution != null) {
            val nextEvolutionAdapter = PokemonEvolutionAdapter(activity!!, pokemon.next_evolution!!)
            recyclerNextEvolution.adapter = nextEvolutionAdapter
        } else {
            nextEmptyView.visibility = View.VISIBLE
            recyclerNextEvolution.visibility = View.GONE
        }

        // Catch pokemon
        catchPokemonButton.setOnClickListener {
            val caughtPokemon =
                PokemonEntity(
                    pokemon.num!!,
                    pokemon.name,
                    pokemon.img,
                    pokemon.height,
                    pokemon.weight
                )
            viewModel.catchPokemon(caughtPokemon)

            val newPokeball = PokeballEntity(pokemon.num, pokemon.name)
            viewModel.addPokeball(newPokeball)

            Toast.makeText(itemView.context, "Pokemon's caught", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private var instance: PokemonDetailFragment?=null

        fun getInstance() : PokemonDetailFragment {
            if (instance == null) {
                instance = PokemonDetailFragment()
            }

            return instance as PokemonDetailFragment
        }
    }
}