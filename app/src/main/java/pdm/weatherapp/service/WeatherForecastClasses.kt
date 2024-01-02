package pdm.weatherapp.service

class WeatherForecastClasses {
    data class Location (
        var name : String? = null,
        var local_names : Map<String, String>? = null,
        var lat : Double? = null,
        var lon: Double? = null,
        var country : String?= null
    )
}