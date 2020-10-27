package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.DiaryEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.DiaryFragment

class DiaryAdapter(internal var context: Context) : RecyclerView.Adapter<DiaryAdapter.ListViewHolder>() {
    private var listDiary: List<DiaryEntity> = listOf()
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ListViewHolder {
        val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.diary_item, view, false)

        return ListViewHolder(itemView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DiaryEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int){
        var currentDiary = listDiary[position]
        holder.content.text = currentDiary.content
        holder.date.text = currentDiary.date

        holder.deleteButton.setOnClickListener {
            onItemClickCallback.onItemClicked(listDiary[holder.adapterPosition])
        }
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
        var deleteButton: Button = itemView.findViewById(R.id.delete_button)
    }
}