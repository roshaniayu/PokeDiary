package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.ui.PokemonList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment = PokemonList()

            when (item.itemId) {
                R.id.nav_home -> selectedFragment = PokemonList()
                R.id.nav_stopwatch -> selectedFragment = PokemonList()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment).commit()

            true
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            PokemonList()
        )
            .commit()
    }
}