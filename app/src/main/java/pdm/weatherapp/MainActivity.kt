package pdm.weatherapp

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import pdm.weatherapp.ui.theme.WeatherAppTheme
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.compose.ui.Modifier

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomePage(this)
                }
            }
        }
    }
}

