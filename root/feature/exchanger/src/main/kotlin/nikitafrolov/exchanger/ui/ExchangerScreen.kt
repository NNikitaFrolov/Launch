package nikitafrolov.exchanger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import nikitafrolov.designsystem.component.PrimaryButton
import nikitafrolov.designsystem.component.Toolbar
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Toolbar(text = stringResource(R.string.exchanger__title))

        //TODO implement core text field
        var text by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "From") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text(text = "0$") },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            modifier = Modifier.padding(20.dp),
            text = "Submit" //TODO set from state
        )
    }
}