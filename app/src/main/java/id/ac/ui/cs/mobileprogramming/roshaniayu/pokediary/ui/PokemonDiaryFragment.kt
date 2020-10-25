package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.MainActivity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.PokemonBoxAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.DiaryViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.PokemonViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.utils.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonDiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        val factoryDiary = InjectorUtils.provideDiaryViewModelFactory(itemView.context)
        val viewModelDiary = ViewModelProviders.of(this, factoryDiary).get(DiaryViewModel::class.java)
        viewModelDiary.getAllDiary().observe(this, Observer { diary ->
            val stringBuilder = StringBuilder()
            if (diary.isNotEmpty()) {
                val latestInput = diary[0]
                latestDiaryTextView.text = latestInput.content
                stringBuilder.append(latestInput.date)
            } else {
                stringBuilder.append("There's no input yet")
            }

            latestDateTextView.text = stringBuilder.toString()
        })

        val readMoreButton: Button = itemView.findViewById(R.id.read_more_button)
        readMoreButton.setOnClickListener(View.OnClickListener {
            val fragmentTransaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, DiaryFragment())
            fragmentTransaction?.addToBackStack("read_more_diary")
            fragmentTransaction?.commit()
        })

        showPokeBoxRecyclerList()

        return itemView
    }

    private fun showPokeBoxRecyclerList() {
        pokeboxRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        pokeboxRecyclerView.setHasFixedSize(true)

        val adapter = PokemonBoxAdapter(activity!!)
        pokeboxRecyclerView.adapter = adapter

        viewModel.getAllPokemon().observe(this, Observer { pokemon ->
            adapter.setPokemon(pokemon)
        })
    }
}