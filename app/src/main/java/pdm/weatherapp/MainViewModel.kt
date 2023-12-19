package pdm.weatherapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.model.User

class FavoriteCitiesViewModel : ViewModel() {
    private val _cities = mutableStateListOf<FavoriteCity>()
    val cities: List<FavoriteCity>
        get() = _cities

    private var _user = mutableStateOf(User("...", "..."))
    var user : User
        get() = _user.value
        set(tmp) {
            _user.value.name = tmp.name
            _user.value.email = tmp.email
        }

    init {
        FirebaseDB.onUserLogin = {
            user = it
        }
        FirebaseDB.onUserLogout = {
            user.name = "..."
        }
        FirebaseDB.onCityAdded = {
            _cities.add(it)
        }
        FirebaseDB.onCityRemoved = {
            _cities.remove(it)
        }
    }
    fun addCity(city: FavoriteCity) {
        FirebaseDB.add(city)
    }

    fun removeCity(city: FavoriteCity) {
        FirebaseDB.remove(city)
    }
}