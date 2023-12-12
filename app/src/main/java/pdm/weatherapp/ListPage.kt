package pdm.weatherapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListPage(viewModel: FavoriteCitiesViewModel) {
    val context = LocalContext.current
    val cityList: List<FavoriteCity> = viewModel.cities

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cityList) { city ->
            FavoriteCityItem(
                favCity = city,
                onClose = {
                    viewModel.remove(city)
                },
                onClick = { clickedCity ->
                    showToast(context, "${clickedCity.cityName} : Tempo : ${clickedCity.currentWeather}")
                }
            )
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun FavoriteCityItem(
    favCity: FavoriteCity,
    onClose: () -> Unit,
    onClick: (FavoriteCity) -> Unit,
    modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick(favCity) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.FavoriteBorder,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = modifier.weight(1f)) {
                Text(modifier = Modifier,
                    text = favCity.cityName,
                    fontSize = 24.sp)
                Text(modifier = Modifier,
                    text = favCity.currentWeather,
                    fontSize = 16.sp)
            }
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        }
}