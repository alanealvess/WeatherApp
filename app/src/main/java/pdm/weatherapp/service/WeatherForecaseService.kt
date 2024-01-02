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
    fun getName(lat: Double, long: Double, onResponse : (String?) -> Unit ) {
        val call: Call<List<WeatherForecastClasses.Location>?> = forecastAPI.getName(lat = lat, lng = long)
        call.enqueue(object : Callback<List<WeatherForecastClasses.Location>?> {
            override fun onResponse(call: Call<List<WeatherForecastClasses.Location>?>,
                                    response: Response<List<WeatherForecastClasses.Location>?>
            ) {
                val list = response.body()
                val name = if (list?.size!! > 0) list[0].name else null
                onResponse(name)
            }
            override fun onFailure(call: Call<List<WeatherForecastClasses.Location>?>, t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
                onResponse(null)
            }
        })
    }
    fun getLocation(name: String, onResponse : (lat:Double?, long:Double?) -> Unit) {
        val call: Call<List<WeatherForecastClasses.Location>?> = forecastAPI.getLocation(name)
        call.enqueue(object : Callback<List<WeatherForecastClasses.Location>?> {
            override fun onResponse(call: Call<List<WeatherForecastClasses.Location>?>,
                                    response: Response<List<WeatherForecastClasses.Location>?>) {
                val list = response.body()
                onResponse(list?.get(0)?.lat, list?.get(0)?.lon)
            }
            override fun onFailure(call: Call<List<WeatherForecastClasses.Location>?>, t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
                onResponse(null, null)
            }
        })
    }
}