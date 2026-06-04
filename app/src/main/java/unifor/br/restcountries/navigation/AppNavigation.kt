package unifor.br.restcountries.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import unifor.br.restcountries.ui.screens.CountryDetailScreen
import unifor.br.restcountries.ui.screens.CountryListScreen
import unifor.br.restcountries.viewmodel.CountriesViewModel

@Composable
fun AppNavigation(viewModel: CountriesViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            CountryListScreen(
                viewModel = viewModel,
                onCountryClick = { officialName ->
                    navController.navigate("detail/$officialName")
                }
            )
        }
        composable(
            route = "detail/{officialName}",
            arguments = listOf(navArgument("officialName") { type = NavType.StringType })
        ) { backStackEntry ->
            val officialName = backStackEntry.arguments?.getString("officialName").orEmpty()
            CountryDetailScreen(
                officialName = officialName,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}