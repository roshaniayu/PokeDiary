package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.MainActivity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.PokemonEvolutionAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.PokemonTypeAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.Common
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.model.Pokemon
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonDetailFragment : Fragment() {
    internal lateinit var pokemonImage: ImageView
    internal lateinit var pokemonName: TextView
    internal lateinit var pokemonHeight: TextView
    internal lateinit var pokemonWeight: TextView
    lateinit var recyclerType: RecyclerView
    lateinit var recyclerWeakness: RecyclerView
    lateinit var recyclerPrevEvolution : RecyclerView
    lateinit var recyclerNextEvolution: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
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

    private fun setDetailPokemon(pokemon: Pokemon) {
        //Load image
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
        }

        if (pokemon.next_evolution != null) {
            val nextEvolutionAdapter = PokemonEvolutionAdapter(activity!!, pokemon.next_evolution!!)
            recyclerNextEvolution.adapter = nextEvolutionAdapter
        }
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