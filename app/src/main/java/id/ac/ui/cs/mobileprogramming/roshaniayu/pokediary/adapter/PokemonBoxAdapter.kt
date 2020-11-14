package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.PokemonEntity

class PokemonBoxAdapter(internal var context: Context) : RecyclerView.Adapter<PokemonBoxAdapter.ListViewHolder>() {
    private var listPokemon: List<PokemonEntity> = listOf()
    private lateinit var onReleaseClickCallback: OnReleaseClickCallback
    private lateinit var onTrainClickCallback: OnTrainClickCallback

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ListViewHolder {
        val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.pokemon_box, view, false)

        return ListViewHolder(itemView)
    }

    interface OnReleaseClickCallback {
        fun onReleaseClicked(data: PokemonEntity)
    }

    fun setOnReleaseClickCallback(onReleaseClickCallback: OnReleaseClickCallback) {
        this.onReleaseClickCallback = onReleaseClickCallback
    }

    interface OnTrainClickCallback {
        fun onTrainClicked(
            data: PokemonEntity,
            releaseButton: Button,
            trainButton: Button,
            disabledCard: LinearLayout,
            trainingTimer: TextView
        )
    }

    fun setOnTrainClickCallback(onTrainClickCallback: OnTrainClickCallback) {
        this.onTrainClickCallback = onTrainClickCallback
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var currentPokemon = listPokemon[position]
        Glide.with(context).load(currentPokemon.img).into(holder.image)
        holder.name.text = currentPokemon.name
        holder.level.text = "Level: " + currentPokemon.level

        holder.releaseButton.setOnClickListener {
            onReleaseClickCallback.onReleaseClicked(listPokemon[holder.adapterPosition])
        }

        holder.trainButton.setOnClickListener {
            onTrainClickCallback.onTrainClicked(listPokemon[holder.adapterPosition], holder.releaseButton, holder.trainButton, holder.disabledCard, holder.trainingTimer)
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
        var trainButton: Button = itemView.findViewById(R.id.train_pokemon)
        var disabledCard: LinearLayout = itemView.findViewById(R.id.disabled_card)
        var trainingTimer: TextView = itemView.findViewById(R.id.training_timer)
    }
}