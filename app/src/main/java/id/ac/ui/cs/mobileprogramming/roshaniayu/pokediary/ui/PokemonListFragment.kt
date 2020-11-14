package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.MainActivity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.PokemonListAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.ItemOffsetDecoration
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_list, container, false)
        (activity as MainActivity).toolbar.title = getString(R.string.dictionary_name)

        recyclerView = itemView.findViewById(R.id.pokemon_recyclerview) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val tabletSize = resources.getBoolean(R.bool.isTablet)
        if (tabletSize) {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerView.layoutManager = GridLayoutManager(activity, 4)
            } else {
                recyclerView.layoutManager = GridLayoutManager(activity, 3)
            }
        } else {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerView.layoutManager = GridLayoutManager(activity, 3)
            } else {
                recyclerView.layoutManager = GridLayoutManager(activity, 2)
            }
        }
        val itemDecoration =
            ItemOffsetDecoration(
                activity!!,
                R.dimen.spacing
            )
        recyclerView.addItemDecoration(itemDecoration)

        if ((activity as MainActivity).mBound) {
            (activity as MainActivity).mService.fetchData()
            (activity as MainActivity).mService.pokemonList.observe(
                this, Observer { listPokemon ->
                    val adapter = PokemonListAdapter( activity!!, listPokemon)
                    recyclerView.adapter = adapter
            })
        }

        return itemView
    }
}