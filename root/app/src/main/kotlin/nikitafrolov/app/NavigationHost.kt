package nikitafrolov.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import nikitafrolov.exchanger.ui.ExchangerRoute
import nikitafrolov.exchanger.ui.exchangerScreen

@Composable
fun NavigationHost(provideNavController: (NavHostController) -> Unit = {}) {
    val navController = rememberNavController()
    provideNavController(navController)

    NavHost(
        navController = navController,
        startDestination = ExchangerRoute,
    ) {
        exchangerScreen()
    }
}