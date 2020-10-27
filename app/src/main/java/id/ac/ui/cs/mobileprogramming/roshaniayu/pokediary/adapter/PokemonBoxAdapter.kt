package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokemonEntity

class PokemonBoxAdapter(internal var context: Context) : RecyclerView.Adapter<PokemonBoxAdapter.ListViewHolder>() {
    private var listPokemon: List<PokemonEntity> = listOf()
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ListViewHolder {
        val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.pokemon_box, view, false)

        return ListViewHolder(itemView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PokemonEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var currentPokemon = listPokemon[position]
        Glide.with(context).load(currentPokemon.img).into(holder.image)
        holder.name.text = currentPokemon.name
        holder.level.text = "Level: " + currentPokemon.level

        holder.releaseButton.setOnClickListener {
            onItemClickCallback.onItemClicked(listPokemon[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listPokemon.size
    }

    fun setPokemon(pokemon: List<PokemonEntity>) {
        listPokemon = pokemon
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.pokemon_image)
        var name: TextView = itemView.findViewById(R.id.pokemon_name)
        var level: TextView = itemView.findViewById(R.id.pokemon_level)
        var releaseButton: Button = itemView.findViewById(R.id.release_pokemon)
    }
}