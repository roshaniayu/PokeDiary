package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.PokemonListAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.Common
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.ItemOffsetDecoration
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.network.PokedexClient
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.network.PokedexService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonList.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonList : Fragment() {
    internal lateinit var recyclerView: RecyclerView
    internal var compositeDisposable = CompositeDisposable()
    internal var pokedexService: PokedexService

    init {
        val retrofit = PokedexClient.instance
        pokedexService = retrofit.create(PokedexService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.pokemon_list, container, false)

        recyclerView = itemView.findViewById(R.id.pokemon_recyclerview) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        val itemDecoration =
            ItemOffsetDecoration(
                activity!!,
                R.dimen.spacing
            )
        recyclerView.addItemDecoration(itemDecoration)

        fetchData()

        return itemView
    }

    private fun fetchData() {
        compositeDisposable.add(pokedexService.pokemonList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ pokedex ->
                Common.pokemonList = pokedex.pokemon!!
                val adapter =
                    PokemonListAdapter(
                        activity!!,
                        Common.pokemonList
                    )

                recyclerView.adapter = adapter
            })
    }
}