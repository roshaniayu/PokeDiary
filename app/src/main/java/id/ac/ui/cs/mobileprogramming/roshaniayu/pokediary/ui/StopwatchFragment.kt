package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.MainActivity
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.R
import kotlinx.android.synthetic.main.activity_main.*

class StopwatchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemView = inflater.inflate(R.layout.fragment_stopwatch, container, false)
        (activity as MainActivity).toolbar.title = getString(R.string.timer_name)

        val stopwatch = itemView.findViewById<TextView>(R.id.stopwatch)
        val startButton = itemView.findViewById<Button>(R.id.start_stopwatch)
        val pauseButton = itemView.findViewById<Button>(R.id.pause_stopwatch)
        val resetButton = itemView.findViewById<Button>(R.id.reset_stopwatch)

        if ((activity as MainActivity).stopwatchIsRunning) {
            startButton.isEnabled = false
            resetButton.isEnabled = false
        }
        (activity as MainActivity).stopwatchText = stopwatch

        startButton.setOnClickListener {
            (activity as MainActivity).startStopwatch()
            startButton.isEnabled = false
            resetButton.isEnabled = false
        }
        pauseButton.setOnClickListener {
            (activity as MainActivity).pauseStopwatch()
            startButton.isEnabled = true
            resetButton.isEnabled = true
        }
        resetButton.setOnClickListener {
            (activity as MainActivity).resetStopwatch()
            stopwatch.text = "00:00:00"
        }

        return itemView
    }
}