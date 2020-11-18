package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.network.model.Pokemon
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.PokemonViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.utils.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ObsoleteCoroutinesApi

class PokemonDetailFragment : Fragment() {
    private lateinit var pokemonImage: ImageView
    private lateinit var pokemonName: TextView
    private lateinit var pokemonHeight: TextView
    private lateinit var pokemonWeight: TextView
    private lateinit var prevEmptyView: TextView
    private lateinit var nextEmptyView: TextView
    private lateinit var saveImageButton: ImageButton
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
        saveImageButton = itemView.findViewById(R.id.save_image)
        catchPokemonButton = itemView.findViewById(R.id.catch_pokemon)
        catchPokemonButton.isEnabled = true

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

    // Method to save an image to gallery and return uri
    private fun saveImage(drawable: Drawable, title:String): Uri {
        // Get the bitmap from drawable object
        val bitmap = (drawable as BitmapDrawable).bitmap

        // Save image to gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
            activity?.contentResolver,
            bitmap,
            title,
            "Image of $title"
        )

        // Parse the gallery image url to uri
        return Uri.parse(savedImageURL)
    }

    @ObsoleteCoroutinesApi
    private fun setDetailPokemon(pokemon: Pokemon) {
        // Load image
        Glide.with(activity!!).load(pokemon.img).into(pokemonImage)

        pokemonName.text = pokemon.name
        pokemonHeight.text = getString(R.string.height) + ": " + pokemon.height
        pokemonWeight.text = getString(R.string.weight) + ": " + pokemon.weight

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

        viewModel.getAllPokemon().observe(this, Observer { pokemonBox ->
            if (pokemonBox.isNotEmpty()) {
                pokemonBox.forEach { caughtPokemon ->
                    if (pokemon.num == caughtPokemon.num) {
                        catchPokemonButton.text = getString(R.string.caught_pokemon)
                        catchPokemonButton.isEnabled = false
                    }
                }
            }
        })

        // Save pokemon image
        saveImageButton.setOnClickListener{
            // Get the image from drawable resource as drawable object
            // Save the image to gallery and get saved image Uri
            val uri = pokemon.name?.let { it1 -> saveImage(pokemonImage.drawable, it1) }
            Toast.makeText(itemView.context, getString(R.string.image_saved) + " " + uri, Toast.LENGTH_SHORT).show()
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

            Toast.makeText(itemView.context, getString(R.string.pokemon_caught), Toast.LENGTH_SHORT).show()
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