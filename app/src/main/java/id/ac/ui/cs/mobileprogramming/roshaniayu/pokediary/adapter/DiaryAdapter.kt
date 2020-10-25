package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.DiaryEntity

class DiaryAdapter(internal var context: Context) : RecyclerView.Adapter<DiaryAdapter.ListViewHolder>() {
    private var listDiary: List<DiaryEntity> = listOf()

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ListViewHolder {
        val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.diary_item, view, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int){
        var currentDiary = listDiary[position]
        holder.content.text = currentDiary.content
        holder.date.text = currentDiary.date
    }

    override fun getItemCount(): Int {
        return listDiary.size
    }

    fun setDiary(diary: List<DiaryEntity>) {
        listDiary = diary
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content: TextView = itemView.findViewById(R.id.diary_content)
        var date: TextView = itemView.findViewById(R.id.diary_date)
    }
}