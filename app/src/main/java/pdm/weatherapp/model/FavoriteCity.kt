package pdm.weatherapp.model

import com.google.firebase.firestore.Exclude
import pdm.weatherapp.service.WeatherForecastClasses

data class FavoriteCity (
    var name: String? = null,
    var latitude : Double? = null,
    var longitude : Double? = null,
    @Exclude
    var currentWeather: WeatherForecastClasses.CurrentWeather? = null
)