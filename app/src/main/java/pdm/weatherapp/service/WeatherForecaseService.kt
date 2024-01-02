package pdm.weatherapp.service

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherForecastService {
    private var forecastAPI: WeatherForecastAPI

    init {
        val retrofitAPI = Retrofit.Builder()
            .baseUrl(WeatherForecastAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        forecastAPI = retrofitAPI.create(WeatherForecastAPI::class.java)
    }

    private
    fun <T> enqueue(call: Call<T?>, onResponse: ((T?) -> Unit)? = null) {
        call.enqueue(object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                val obj: T? = response.body()
                onResponse?.invoke(obj)
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
            }
        })
    }
    fun getName(lat: Double, long: Double, onResponse: (String?) -> Unit) {
        val call: Call<List<WeatherForecastClasses.Location>?> = forecastAPI.getName(lat = lat, lng = long)
        enqueue(call) { list ->
            val name = if (list?.size!! > 0) list[0].name else null
            onResponse(name)
        }
    }
    fun getLocation(name: String, onResponse: (lat: Double?, long: Double?) -> Unit) {
        val call: Call<List<WeatherForecastClasses.Location>?> = forecastAPI.getLocation(name)
        enqueue(call) { list ->
            onResponse(list?.get(0)?.lat, list?.get(0)?.lon)
        }
    }
    fun getCurrentWeather(name: String, onResponse : (WeatherForecastClasses.CurrentWeather?) -> Unit) {
        val call: Call<WeatherForecastClasses.CurrentWeather?> = forecastAPI.getCurrentWeather(name)
        enqueue(call) { onResponse.invoke(it) }
    }
    fun getForecast(name: String, onResponse : (WeatherForecastClasses.WeatherForecast?) -> Unit) {
        val call: Call<WeatherForecastClasses.WeatherForecast?> = forecastAPI.getForecast(name)
        enqueue(call) { onResponse.invoke(it) }
    }
}