package pdm.weatherapp.repo

import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.model.User
import pdm.weatherapp.service.WeatherForecastService

object Repository {
    var onUserLogin: ((User) -> Unit)? = null
    var onUserLogout: (() -> Unit)? = null
    var onCityAdded: ((FavoriteCity) -> Unit)? = null
    var onCityRemoved: ((FavoriteCity) -> Unit)? = null
    init {
        FirebaseDB.onUserLogin = { onUserLogin?.invoke(it) }
        FirebaseDB.onUserLogout = { onUserLogout?.invoke() }
        FirebaseDB.onCityAdded = { city -> onCityAdded?.invoke(city) }
        FirebaseDB.onCityRemoved = { onCityRemoved?.invoke(it) }
    }
    fun addCity(name : String) {
        WeatherForecastService.getLocation(name) { lat, long ->
            FirebaseDB.add(FavoriteCity(name, lat?:0.0, long?:0.0))
        }
    }
    fun addCity(lat: Double, long: Double) {
        WeatherForecastService.getName(lat, long){ name ->
            val cityName = name ?: ("Erro@$lat:$long")
            FirebaseDB.add(FavoriteCity(cityName, lat, long))
        }
    }
    fun remove(city: FavoriteCity) {
        FirebaseDB.remove(city)
    }
    fun register(userName: String, email: String) {
        FirebaseDB.register(userName, email)
    }
}