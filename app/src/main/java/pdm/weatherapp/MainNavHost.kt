package pdm.weatherapp

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavHost(navController: NavHostController, viewModel: FavoriteCitiesViewModel, context: Context) {
    NavHost(navController, startDestination = BottomNavItem.HomePage.route) {
        composable(route = BottomNavItem.HomePage.route) {
            HomePage(viewModel = viewModel, context = context)
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(viewModel = viewModel, context = context)
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage(viewModel = viewModel, context = context)
        }
    }
}