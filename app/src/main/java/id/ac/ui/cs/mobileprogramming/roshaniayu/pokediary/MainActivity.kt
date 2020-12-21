package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary

import android.content.*
import android.os.*
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.broadcastreceiver.ShowDetailReceiver
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.broadcastreceiver.ShowEvolutionReceiver
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.Common
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.service.FetchService
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.PokemonDetailFragment
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.PokemonDiaryFragment
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.PokemonListFragment
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.StopwatchFragment

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
    lateinit var mService: FetchService
    var mBound: Boolean = false

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    // Defines callbacks for service binding, passed to bindService()
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as FetchService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
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

        // Create broadcast handle
        val showDetail = object : ShowDetailReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action.toString() == Common.KEY_ENABLE_HOME) {
                    // Replace fragment
                    val detailFragment = PokemonDetailFragment.getInstance()
                    val position = intent.getIntExtra("position", -1)
                    val bundle = Bundle()
                    bundle.putInt("position", position)
                    detailFragment.arguments = bundle

                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, detailFragment)
                    fragmentTransaction.addToBackStack("detail_pokemon")
                    fragmentTransaction.commit()
                }
            }
        }
        val showEvolution = object : ShowEvolutionReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action.toString() == Common.KEY_NUM_EVOLUTION) {
                    // Replace fragment
                    val detailFragment = PokemonDetailFragment.getInstance()
                    val num = intent.getStringExtra("num")
                    val bundle = Bundle()
                    bundle.putString("num", num)
                    detailFragment.arguments = bundle

                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.remove(detailFragment) // Remove current
                    fragmentTransaction.replace(R.id.fragment_container, detailFragment)
                    fragmentTransaction.addToBackStack("detail_pokemon")
                    fragmentTransaction.commit()
                }
            }
        }

        // Register broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))
        LocalBroadcastManager.getInstance(this).registerReceiver(showEvolution, IntentFilter(Common.KEY_NUM_EVOLUTION))

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment = PokemonDiaryFragment()

            when (item.itemId) {
                R.id.nav_home -> selectedFragment = PokemonDiaryFragment()
                R.id.nav_explore -> selectedFragment = PokemonListFragment()
                R.id.nav_stopwatch -> selectedFragment = StopwatchFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment).commit()

            true
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            PokemonDiaryFragment()).commit()
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        Intent(this, FetchService::class.java).also { intent ->
            startService(intent)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Clear all fragment in stack with name 'detail_pokemon'
                supportFragmentManager.popBackStack("detail_pokemon", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }

        return true
    }

    fun hideKeyboard() {
        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
    }

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

    external fun increaseLevel(level: Int): Int
}