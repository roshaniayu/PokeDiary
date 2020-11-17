package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.MainActivity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.adapter.DiaryAdapter
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.entity.DiaryEntity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.viewmodel.DiaryViewModel
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.utils.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class DiaryFragment : Fragment() {
    private lateinit var itemView: View
    private lateinit var viewModel: DiaryViewModel
    private lateinit var diaryRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_diary, container, false)
        val factory = InjectorUtils.provideDiaryViewModelFactory(itemView.context)
        viewModel = ViewModelProviders.of(this, factory).get(DiaryViewModel::class.java)

        (activity as MainActivity).toolbar.title = getString(R.string.app_name)
        diaryRecyclerView = itemView.findViewById(R.id.diary_recyclerview)
        val diaryInput: EditText = itemView.findViewById(R.id.text_diary)
        val inputButton: Button = itemView.findViewById(R.id.input_button)

        inputButton.setOnClickListener {
            val diaryString = diaryInput.text.toString()

            if (diaryString.trim().isEmpty()) {
                (activity as MainActivity).hideKeyboard()

                val warningToast = Toast.makeText(activity, getString(R.string.field_empty), Toast.LENGTH_SHORT)
                warningToast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 200)
                warningToast.show()
            } else {
                (activity as MainActivity).hideKeyboard()
                val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
                val dateString: String = simpleDateFormat.format(Date())

                val newDiary = DiaryEntity(diaryString, dateString)
                viewModel.addDiary(newDiary)
                Toast.makeText(activity, getString(R.string.diary_created), Toast.LENGTH_SHORT).show()
            }

            diaryInput.setText("")
        }

        showDiaryRecyclerList()

        return itemView
    }

    fun deleteDiary(diary: DiaryEntity) {
        // Build an AlertDialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        // Set a title for alert dialog
        builder.setTitle(getString(R.string.delete_alert_dialog))

        // Ask the final question
        builder.setMessage(getString(R.string.delete_alert_message))

        // Set click listener for alert dialog buttons
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.deleteDiary(diary)
                        Toast.makeText(itemView.context, getString(R.string.diary_deleted), Toast.LENGTH_SHORT).show()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        // Set the alert dialog yes button click listener
        builder.setPositiveButton(getString(R.string.yes), dialogClickListener)

        // Set the alert dialog no button click listener
        builder.setNegativeButton(getString(R.string.no), dialogClickListener)

        val dialog: AlertDialog = builder.create()
        // Display the alert dialog on interface
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#808080"))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#808080"))
    }

    private fun showDiaryRecyclerList() {
        val diaryEmpty: TextView = itemView.findViewById(R.id.diary_empty)
        diaryRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        diaryRecyclerView.setHasFixedSize(true)

        val adapter = DiaryAdapter(activity!!)
        diaryRecyclerView.adapter = adapter

        viewModel.getAllDiary().observe(this, Observer { diary ->
            if (diary.isNotEmpty()) {
                diaryEmpty.visibility = View.GONE
            } else {
                diaryEmpty.visibility = View.VISIBLE
            }

            adapter.setDiary(diary)
        })

        adapter.setOnItemClickCallback(object: DiaryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DiaryEntity) {
                deleteDiary(data)
            }
        })
    }
}