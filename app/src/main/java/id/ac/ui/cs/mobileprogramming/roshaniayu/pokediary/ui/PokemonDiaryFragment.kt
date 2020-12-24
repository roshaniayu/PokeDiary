package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.MainActivity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.PokemonBoxAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokemonEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.DiaryViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.PokemonViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.utils.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*

class PokemonDiaryFragment : Fragment() {
    private lateinit var itemView: View
    private lateinit var viewModel: PokemonViewModel
    private lateinit var pokeboxRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_pokemon_diary, container, false)
        val factory = InjectorUtils.providePokemonViewModelFactory(itemView.context)
        viewModel = ViewModelProviders.of(this, factory).get(PokemonViewModel::class.java)

        (activity as MainActivity).toolbar.title = getString(R.string.app_name)
        pokeboxRecyclerView = itemView.findViewById(R.id.pokemon_box_recyclerview)

        val latestDiaryTextView: TextView = itemView.findViewById(R.id.latest_diary)
        val latestDateTextView: TextView = itemView.findViewById(R.id.latest_date)
        val readMoreButton: Button = itemView.findViewById(R.id.read_more_button)
        val factoryDiary = InjectorUtils.provideDiaryViewModelFactory(itemView.context)
        val viewModelDiary = ViewModelProviders.of(this, factoryDiary).get(DiaryViewModel::class.java)
        viewModelDiary.getAllDiary().observe(this, Observer { diary ->
            if (diary.isNotEmpty()) {
                val latestInput = diary[0]
                latestDiaryTextView.text = latestInput.content
                latestDateTextView.text = latestInput.date
                readMoreButton.text = getString(R.string.read_more_button)
            } else {
                latestDiaryTextView.text = getString(R.string.diary_empty)
                latestDateTextView.visibility = View.GONE
                readMoreButton.text = getString(R.string.input_button)
            }
        })

        readMoreButton.setOnClickListener {
            val fragmentTransaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, DiaryFragment())
            fragmentTransaction?.addToBackStack("read_more_diary")
            fragmentTransaction?.commit()
        }

        showPokeBoxRecyclerList()

        return itemView
    }

    fun releasePokemon(pokemon: PokemonEntity) {
        // Build an AlertDialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        // Set a title for alert dialog
        builder.setTitle(getString(R.string.release_alert_dialog))

        // Ask the final question
        builder.setMessage(getString(R.string.release_alert_message))

        // Set click listener for alert dialog buttons
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.deletePokemon(pokemon)
                        Toast.makeText(itemView.context, getString(R.string.pokemon_released), Toast.LENGTH_SHORT).show()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        // Set the alert dialog yes button click listener
        builder.setPositiveButton(getString(R.string.yes), dialogClickListener)

        // Set the alert dialog no button click listener
        builder.setNegativeButton(getString(R.string.no), dialogClickListener)

        val dialog: AlertDialog = builder.create()
        // Display the alert dialog on interface
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#808080"))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#808080"))
    }

    fun trainPokemon(pokemon: PokemonEntity, releaseButton: Button, trainButton: Button,
                     disabledCard: LinearLayout, trainingTimer: TextView) {
        releaseButton.isEnabled = false
        trainButton.isEnabled = false
        disabledCard.visibility = View.VISIBLE

        (activity as MainActivity).startTraining(pokemon, trainingTimer, viewModel, releaseButton, trainButton, disabledCard)
    }

    private fun showPokeBoxRecyclerList() {
        val pokemonBoxEmpty: TextView = itemView.findViewById(R.id.pokemon_box_empty)
        pokeboxRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        pokeboxRecyclerView.setHasFixedSize(true)

        val adapter = PokemonBoxAdapter(activity!!)
        pokeboxRecyclerView.adapter = adapter

        viewModel.getAllPokemon().observe(this, Observer { pokemon ->
            if (pokemon.isNotEmpty()) {
                pokemonBoxEmpty.visibility = View.GONE
            } else {
                pokemonBoxEmpty.visibility = View.VISIBLE
            }

            adapter.setPokemon(pokemon)
        })

        adapter.setOnReleaseClickCallback(object: PokemonBoxAdapter.OnReleaseClickCallback {
            override fun onReleaseClicked(data: PokemonEntity) {
                releasePokemon(data)
            }
        })

        adapter.setOnTrainClickCallback(object: PokemonBoxAdapter.OnTrainClickCallback {
            override fun onTrainClicked(
                data: PokemonEntity,
                releaseButton: Button,
                trainButton: Button,
                disabledCard: LinearLayout,
                trainingTimer: TextView
            ) {
                trainPokemon(data, releaseButton, trainButton, disabledCard, trainingTimer)
            }
        })
    }
}