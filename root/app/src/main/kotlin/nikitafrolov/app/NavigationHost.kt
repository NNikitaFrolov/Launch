package nikitafrolov.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationHost(provideNavController: (NavHostController) -> Unit = {}) {
    val navController = rememberNavController()
    provideNavController(navController)

    NavHost(
        navController = navController,
        startDestination = "exchanger", //TODO need implement feature
    ) {
        composable(
            route = "exchanger"
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(text = "Hello World!", modifier = Modifier.wrapContentSize())
            }
        }
    }
}