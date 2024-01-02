package pdm.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.repo.Repository

@Composable
fun MapPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    context: Context,
    camPosState: CameraPositionState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_700))
            .wrapContentSize(Alignment.Center)
    ) {
        val hasLocationPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED
            )
        }

        var cityName by remember { mutableStateOf("") }

        GoogleMap (
            modifier = Modifier.fillMaxSize(),
            onMapClick = {
                Repository.addCity(lat = it.latitude, long = it.longitude)
                Toast.makeText(context,"Toque no mapa", Toast.LENGTH_SHORT).show()},
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(myLocationButtonEnabled = true),
            cameraPositionState = camPosState
        ) {
            viewModel.cities.forEach {
                if (it.latitude != null && it.longitude != null) {
                    Marker( state = MarkerState(position = LatLng(it.latitude!!, it.longitude!!)),
                        title = "${it.name}", snippet = "${LatLng(it.latitude!!, it.longitude!!)}")
                }
            }
        }
    }
}