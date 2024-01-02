package pdm.weatherapp.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import pdm.weatherapp.BuildConfig

interface WeatherForecastAPI {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val APPID = BuildConfig.OWM_API_KEY
    }
    @GET("geo/1.0/reverse?APPID=$APPID&mode=json&units=metric&limit=1&lang=pt_br")
    fun getName(@Query("lat") lat: Double, @Query("lon") lng: Double):
            Call<List<WeatherForecastClasses.Location>?>
    @GET("geo/1.0/direct?APPID=$APPID&mode=json&units=metric&limit=1&lang=pt_br")
    fun getLocation(@Query("q") name: String): Call<List<WeatherForecastClasses.Location>?>
}