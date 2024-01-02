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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.repo.Repository

@Composable
fun ListPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    context: Context
) {
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
                    Repository.remove(city)
                },
                onClick = { clickedCity ->
                    val desc = clickedCity.currentWeather?.weather?.get(0)?.description?:
                    "Carregando clima..."
                    Toast.makeText(context, "${clickedCity.name} : Tempo : ${desc}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
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
            val desc = favCity.currentWeather?.weather?.get(0)?.description?:
                        "Carregando clima..."
            Icon(
                Icons.Rounded.FavoriteBorder,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = modifier.weight(1f)) {
                Text(modifier = Modifier,
                    text = favCity.name!!,
                    fontSize = 24.sp)
                Text(modifier = Modifier,
                    text = desc,
                    fontSize = 16.sp)
            }
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        }
}