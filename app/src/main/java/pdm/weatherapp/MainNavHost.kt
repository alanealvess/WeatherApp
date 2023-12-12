package pdm.weatherapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavHost(navController: NavHostController, viewModel: FavoriteCitiesViewModel) {
    NavHost(navController, startDestination = BottomNavItem.HomePage.route) {
        composable(route = BottomNavItem.HomePage.route) {
            HomePage()
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(viewModel)
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage()
        }
    }
}