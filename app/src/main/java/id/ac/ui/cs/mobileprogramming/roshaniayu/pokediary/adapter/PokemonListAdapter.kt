package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.Common
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.model.Pokemon

class PokemonListAdapter(internal var context: Context, internal var pokemonList: List<Pokemon>)
    : RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(pokemonList[position].img).into(holder.pokemon_image)
        holder.pokemon_name.text = pokemonList[position].name
        holder.setItemClickListener(object :
            ItemClickListener {
            override fun onClick(view: View, position: Int) {
                // Toast.makeText(context, "Clicked at pokemon: " + pokemonList[position].name, Toast.LENGTH_SHORT).show()
                LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("position", position))
            }
        })
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        internal var pokemon_image: ImageView
        internal var pokemon_name: TextView
        internal var itemClickListener: ItemClickListener? = null

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        init {
            pokemon_image = itemView.findViewById(R.id.pokemon_image) as ImageView
            pokemon_name = itemView.findViewById(R.id.pokemon_name) as TextView

            itemView.setOnClickListener { view -> itemClickListener?.onClick(view,adapterPosition) }
        }
    }
}