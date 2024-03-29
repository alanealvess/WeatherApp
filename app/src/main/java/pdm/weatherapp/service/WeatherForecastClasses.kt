package pdm.weatherapp.service

class WeatherForecastClasses {
    data class Location (
        var name : String? = null,
        var local_names : Map<String, String>? = null,
        var lat : Double? = null,
        var lon: Double? = null,
        var country : String?= null
    )
    data class Coordinates (
        var lon: Float? = null,
        var lat: Float? = null
    )
    data class Weather (
        var id: String? = null,
        var main: String? = null,
        var description: String? = null,
        var icon: String? = null
    )
    data class TemperaturePressure (
        var temp : Double? = null,
        var feels_like : Double? = null,
        var temp_min : Double? = null,
        var temp_max : Double? = null,
        var temp_kf : Double? = null,
        var pressure : Double? = null,
        var sea_level : Double? = null,
        var grnd_level : Double? = null
    )
    data class CurrentWeather (
        var id : Long? = null,
        var cod : Long? = null,
        var name : String? = null,
        var dt: Long? = 0,
        var base : String? = null,
        var timezone : Long? = null,
        var visibility: Long? = null,
        var coord : Coordinates? = null,
        var weather: Array<Weather>? = null,
        var main : TemperaturePressure? = null,
        var clouds: Map<String, String>? = null,
        var rain: Map<String, Double?>? = null,
        var wind: Map<String, Double?>? = null,
        var snow: Map<String, Double?>? = null,
        var sys: Map<String, String?>? = null,
    )
    data class CityDesc (
        var id: String? = null,
        var name: String? = null,
        var country: String? = null,
        var population: Long = 0,
        var timezone: Long = 0,
        var coord: Coordinates? = null
    )
    data class Forecast (
        var dt: Long = 0,
        var dt_txt: String? = null,
        var main: TemperaturePressure? = null,
        var weather: Array<Weather>? = null,
        var clouds: Map<String, String>? = null,
        var wind: Map<String, Double?>? = null,
        var visibility: Long? = null,
        var pop: Double? = null,
        var rain: Map<String, Double?>? = null,
        var sys: Map<String, String?>? = null,
    )
    data class WeatherForecast (
        var cod: String? = null,
        var message: Float? = null,
        var cnt: Int? = null,
        var list: List<Forecast>? = null,
        var city: CityDesc? = null
    )
}