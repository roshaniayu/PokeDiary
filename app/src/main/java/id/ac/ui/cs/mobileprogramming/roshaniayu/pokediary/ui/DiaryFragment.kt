package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

/**
 * A simple [Fragment] subclass.
 * Use the [DiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

                val warningToast = Toast.makeText(activity, "Field can't be empty", Toast.LENGTH_SHORT)
                warningToast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 200)
                warningToast.show()
            } else {
                (activity as MainActivity).hideKeyboard()
                val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
                val dateString: String = simpleDateFormat.format(Date())

                val newDiary = DiaryEntity(diaryString, dateString)
                viewModel.addDiary(newDiary)
                val submitToast = Toast.makeText(activity, "Diary's created", Toast.LENGTH_SHORT)
                submitToast.show()
            }

            diaryInput.setText("")
        }

        showDiaryRecyclerList()

        return itemView
    }

    private fun showDiaryRecyclerList() {
        diaryRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        diaryRecyclerView.setHasFixedSize(true)

        val adapter = DiaryAdapter(activity!!)
        diaryRecyclerView.adapter = adapter

        viewModel.getAllDiary().observe(this, Observer { diary ->
            adapter.setDiary(diary)
        })
    }
}