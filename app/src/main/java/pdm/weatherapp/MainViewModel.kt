package pdm.weatherapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.model.User
import pdm.weatherapp.repo.Repository

class MainViewModel : ViewModel() {
    private val _cities = mutableStateMapOf<String, FavoriteCity>()
    val cities: List<FavoriteCity>
        get() = _cities.values.toList()

    private var _user = mutableStateOf(User("...", "..."))

    var user : User
        get() = _user.value
        set(value) {
            _user.value = value
        }

    init {
        Repository.onUserLogin = {
            user = it
        }
        Repository.onUserLogout = {
            user.name = "..."
        }
        Repository.onCityAdded = {
            _cities[it.name!!] = it
        }
        Repository.onCityRemoved = {
            _cities.remove(it.name)
        }
        Repository.onCityUpdated = {
            // Força recomposição
            _cities.remove(it.name)
            _cities[it.name!!] = it.copy()
        }
    }
    fun addCity(city: FavoriteCity) {
        FirebaseDB.add(city)
    }

    fun removeCity(city: FavoriteCity) {
        FirebaseDB.remove(city)
    }
}