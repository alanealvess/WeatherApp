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
    var onCityUpdated: ((FavoriteCity) -> Unit)? = null
    init {
        FirebaseDB.onUserLogin = { onUserLogin?.invoke(it) }
        FirebaseDB.onUserLogout = { onUserLogout?.invoke() }
        FirebaseDB.onCityAdded = { city ->
            onCityAdded?.invoke(city)
            WeatherForecastService.getCurrentWeather(city.name!!) { currWeather ->
                currWeather?.let { cw ->
                    city.currentWeather = cw
                    onCityUpdated?.invoke(city)
                    // Carrega imagem se pegou weather
                    cw.weather?.get(0)?.let { w ->
                        // Pega icon id e nome do arquivo
                        val imgFile = getImageFileName(w.icon)
                        // Carrega URL do arquivo no
                        FirebaseDB.getFileURL(imgFile) { url ->
                            // URL is used by AsyncImage
                            city.imageUrl = url
                            // update UI when url available
                            onCityUpdated?.invoke(city)
                            if (url != null) {
                                // Load bitmap, used in Map
                                WeatherForecastService.getBitmap(url) {
                                    city.bitmap = it
                                    // update UI when bitmap loaded
                                    onCityUpdated?.invoke(city)
                                }
                            }
                        }
                    }
                }
            }
        }
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
    fun loadForecast(city : FavoriteCity) {
        WeatherForecastService.getForecast(city.name!!) {
            city.forecast = it
            onCityUpdated?.invoke(city)
        }
    }
    fun getImageFileName(icon: String?): String {
        val icon = icon ?: "13d" // default/error = snow
        return "img/$icon@4x.png"
    }
}