package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.network.model

class Pokemon {
    var id = 0
    var num: String? = null
    var name: String? = null
    var img: String? = null
    var type: List<String>? = null
    var height: String? = null
    var weight: String? = null
    var candy: String? = null
    var candy_count = 0
    var egg: String? = null
    var spawn_chance = 0.0
    var avg_spawns = 0.0
    var spawn_time: String? = null
    var multipliers: List<Double>? = null
    var weaknesses: List<String>? = null
    var next_evolution: List<Evolution>? = null
    var prev_evolution: List<Evolution>? = null
}