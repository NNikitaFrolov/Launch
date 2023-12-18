package nikitafrolov.exchanger.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val exchangerRoute = "exchanger"

fun NavGraphBuilder.exchangerScreen(
    onBack: () -> Unit,
) {
    composable(exchangerRoute) {
        ExchangerScreen()
    }
}

@Composable
internal fun ExchangerScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Hello World!", modifier = Modifier.wrapContentSize())
    }
}