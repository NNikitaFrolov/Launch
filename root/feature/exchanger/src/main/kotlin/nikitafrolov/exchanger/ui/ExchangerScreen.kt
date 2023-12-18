package nikitafrolov.exchanger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import nikitafrolov.designsystem.theme.LaunchTheme
import nikitafrolov.feature.exchanger.R

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
    ExchangerContent()
}

@Preview
@Composable
private fun ExchangerPreview() {
    LaunchTheme {
        ExchangerContent()
    }
}

@Composable
private fun ExchangerContent() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
        ) {
            Text(
                text = stringResource(R.string.exchanger__title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .align(Alignment.Center)
                    .padding(16.dp, 16.dp)
            )
        }

    }
}