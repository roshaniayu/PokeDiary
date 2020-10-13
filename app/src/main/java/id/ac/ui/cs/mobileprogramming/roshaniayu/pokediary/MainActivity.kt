package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.Common
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.PokemonDetailFragment
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.PokemonListFragment
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.StopwatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var stopwatchIsRunning: Boolean = false
    var stopwatchText: TextView? = null
    private var millisecondTime: Long = 0
    private var startTime: Long = 0
    private var timeBuff: Long = 0
    private var updateTime: Long = 0
    private var seconds: Int = 0
    private var minutes: Int = 0
    private var milliSeconds: Int = 0
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    // Create broadcast handle
    private val showDetail = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.toString() == Common.KEY_ENABLE_HOME) {
//                supportActionBar?.setDisplayHomeAsUpEnabled(true)
//                supportActionBar?.setDisplayShowHomeEnabled(true)

                // Replace fragment
                val detailFragment = PokemonDetailFragment.getInstance()
                val position = intent?.getIntExtra("position", -1)
                val bundle = Bundle()
                bundle.putInt("position", position!!)
                detailFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, detailFragment)
                fragmentTransaction.addToBackStack("detail_pokemon")
                fragmentTransaction.commit()

                // Set pokemon name to toolbar
                val pokemon = Common.pokemonList[position]
                toolbar.title = pokemon.name
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mHandler = Handler(Looper.getMainLooper())
        mRunnable = Runnable {
            millisecondTime = SystemClock.uptimeMillis() - startTime
            updateTime = timeBuff + millisecondTime;
            seconds = (updateTime / 1000).toInt();
            minutes = seconds / 60;
            seconds %= 60;
            milliSeconds = (updateTime % 100).toInt();
            mHandler.postDelayed(mRunnable, 0);
            stopwatchText?.text =
                (String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%02d", milliSeconds));
        }

        // Register broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment = PokemonListFragment()

            when (item.itemId) {
                R.id.nav_home -> selectedFragment = PokemonListFragment()
                R.id.nav_stopwatch -> selectedFragment = StopwatchFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment).commit()

            true
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            PokemonListFragment()
        )
            .commit()
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                toolbar.title = getString(R.string.app_name)
//
//                // Clear all fragment in stack with name 'detail_pokemon'
//                supportFragmentManager.popBackStack("detail_pokemon", FragmentManager.POP_BACK_STACK_INCLUSIVE)
//
//                supportActionBar?.setDisplayShowHomeEnabled(false)
//                supportActionBar?.setDisplayHomeAsUpEnabled(false)
//            }
//        }
//
//        return true
//    }

    fun startStopwatch() {
        startTime = SystemClock.uptimeMillis()
        mHandler.postDelayed(mRunnable, 0)
        stopwatchIsRunning = true
    }

    fun pauseStopwatch() {
        timeBuff += millisecondTime
        mHandler.removeCallbacks(mRunnable)
        stopwatchIsRunning = false
    }

    fun resetStopwatch() {
        millisecondTime = 0
        startTime = 0
        timeBuff = 0
        updateTime = 0
        seconds = 0
        minutes = 0
        milliSeconds = 0
    }
}