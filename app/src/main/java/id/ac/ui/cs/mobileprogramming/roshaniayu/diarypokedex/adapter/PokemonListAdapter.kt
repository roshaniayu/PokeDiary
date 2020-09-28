package id.ac.ui.cs.mobileprogramming.roshaniayu.diarypokedex.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.roshaniayu.diarypokedex.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.diarypokedex.model.Pokemon
import kotlinx.android.synthetic.main.pokemon_item.view.*

class PokemonListAdapter(internal var context: Context,
                         internal var pokemonList: List<Pokemon>): RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        internal var pokemon_image: ImageView
        internal var pokemon_name: TextView
        init {
            pokemon_image = itemView.findViewById(R.id.pokemon_image) as ImageView
            pokemon_name = itemView.findViewById(R.id.pokemon_name) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}