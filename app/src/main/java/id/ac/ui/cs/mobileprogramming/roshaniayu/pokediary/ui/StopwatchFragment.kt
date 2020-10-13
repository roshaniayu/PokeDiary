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

class StopwatchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.stopwatch, container, false)
        val stopwatch = view.findViewById<TextView>(R.id.stopwatch)
        val startButton = view.findViewById<Button>(R.id.start_stopwatch)
        val pauseButton = view.findViewById<Button>(R.id.pause_stopwatch)
        val resetButton = view.findViewById<Button>(R.id.reset_stopwatch)

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

        return view
    }
}