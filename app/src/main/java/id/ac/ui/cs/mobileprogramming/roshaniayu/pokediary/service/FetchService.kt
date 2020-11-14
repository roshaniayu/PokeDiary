package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.common.Common
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.model.Pokemon
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.network.PokedexClient
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.network.PokedexService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FetchService : Service() {
    var pokemonList = MutableLiveData<List<Pokemon>>()
    private var compositeDisposable = CompositeDisposable()
    private var pokedexService: PokedexService
    // Binder given to clients
    private val binder = LocalBinder()

    init {
        val retrofit = PokedexClient.instance
        pokedexService = retrofit.create(PokedexService::class.java)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        compositeDisposable.add(pokedexService.pokemonList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ pokedex ->
                Common.pokemonList = pokedex.pokemon!!

                pokemonList.value = Common.pokemonList
            })

        return START_STICKY
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): FetchService = this@FetchService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun fetchData() {
        compositeDisposable.add(pokedexService.pokemonList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ pokedex ->
                Common.pokemonList = pokedex.pokemon!!

                pokemonList.value = Common.pokemonList
            })
    }
}