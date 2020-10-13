package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.model.Pokemon

class PokemonListAdapter(internal var context: Context, internal var pokemonList: List<Pokemon>)
    : RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        internal var pokemon_image: ImageView
        internal var pokemon_name: TextView
        init {
            pokemon_image = itemView.findViewById(R.id.pokemon_image) as ImageView
            pokemon_name = itemView.findViewById(R.id.pokemon_name) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.pokemon_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(pokemonList[position].img).into(holder.pokemon_image)
        holder.pokemon_name.text = pokemonList[position].name
    }

}