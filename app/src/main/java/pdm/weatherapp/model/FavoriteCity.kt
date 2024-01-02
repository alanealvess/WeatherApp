package pdm.weatherapp.model

import android.graphics.Bitmap
import com.google.firebase.firestore.Exclude
import pdm.weatherapp.service.WeatherForecastClasses

data class FavoriteCity (
    var name: String? = null,
    var latitude : Double? = null,
    var longitude : Double? = null,
    @Exclude
    var imageUrl : String? = null,
    @Exclude
    var bitmap : Bitmap? = null,
    @Exclude
    var currentWeather: WeatherForecastClasses.CurrentWeather? = null,
    @Exclude
    var forecast: WeatherForecastClasses.WeatherForecast? = null
)