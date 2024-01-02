package pdm.weatherapp

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.repo.Repository
import pdm.weatherapp.service.WeatherForecastClasses
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    context: Context,
    navCtrl: NavHostController
) {
    val format = DecimalFormat("#.0")
    val temp = format.format(viewModel.city.currentWeather?.main?.temp?:0)
    val desc = viewModel.city.currentWeather?.weather?.get(0)?.description?:"Carregando ..."
    val list = viewModel.forecastImg.keys.toList().sortedBy { it.dt }
    viewModel.city.forecast?.list?.forEach { forecast ->
        val imgFile = Repository.getImageFileName(forecast.weather?.get(0)?.icon?:"13d")
        FirebaseDB.getFileURL(imgFile) { url ->
            viewModel.forecastImg += forecast to url
        }
    }
    Column {
        Row {
            // Substitui o componente Icon
            AsyncImage(
                model = viewModel.city.imageUrl,
                modifier = Modifier.size(130.dp),
                error = painterResource(id = R.drawable.loading),
                contentDescription = "Image"
            )
            val format = DecimalFormat("#.0")
            val temp = format.format(
                viewModel.city.currentWeather?.main?.temp ?: 0
            )
            val desc =
                viewModel.city.currentWeather?.
                weather?.get(0)?.description ?: "Carregando ..."
            Column {

                Spacer(modifier = Modifier.size(20.dp))
                Text(text = "${viewModel.city.name}", fontSize = 32.sp)
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Clima: $desc", fontSize = 20.sp)
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Temp: $temp℃", fontSize = 20.sp)
            }
        }
        LazyColumn {
            viewModel.city.forecast?.list?.let {
                items(list) { forecast ->
                    CityForecastItem (
                        forecast = forecast,
                        imageUrl = viewModel.forecastImg[forecast],
                        onClick = { },
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
fun CityForecastItem(
    forecast: WeatherForecastClasses.Forecast,
    imageUrl: String?,
    onClick: (WeatherForecastClasses.Forecast) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onClick(forecast) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var date = forecast.dt_txt?:"???"
        date = date.substring(0, date.lastIndexOf(':'))
        val formattedDate = "${date.substring(8, 10)}-${date.substring(5, 7)}-${date.substring(0, 4)} ${date.substring(11)}"

        val format = DecimalFormat("#.0")
        val tempMin = format.format(forecast.main?.temp_min)
        val tempMax = format.format(forecast.main?.temp_max)
        AsyncImage(
            model = imageUrl,
            modifier = Modifier.height(60.dp).width(60.dp),
            error = painterResource(id = R.drawable.loading),
            contentDescription = "Description"
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(modifier = Modifier, text = "$formattedDate", fontSize = 20.sp)
        Spacer(modifier = Modifier.size(15.dp))
        Text(modifier = Modifier, text = "$tempMin℃", fontSize = 20.sp)
        Spacer(modifier = Modifier.size(15.dp))
        Text(modifier = Modifier, text = "$tempMax℃", fontSize = 20.sp)
    }
}