package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.robertlevonyan.views.chip.Chip
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.Common

class PokemonTypeAdapter(internal var context: Context, internal var typeList: List<String>) : RecyclerView.Adapter<PokemonTypeAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chip.chipText = typeList[position]
        holder.chip.changeBackgroundColor(Common.getColorByType(typeList[position]))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var chip: Chip

        init {
            chip = itemView.findViewById(R.id.chip) as  Chip
            chip.setOnChipClickListener { view -> Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show() }
        }
    }
}