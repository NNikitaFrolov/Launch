package nikitafrolov.exchanger.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import nikitafrolov.designsystem.component.InputNumberDecimal
import nikitafrolov.designsystem.component.PrimaryButton
import nikitafrolov.designsystem.component.Toolbar
import nikitafrolov.designsystem.icon.LaunchIcons
import nikitafrolov.designsystem.theme.LaunchTheme
import nikitafrolov.designsystem.tools.clickableThrottle
import nikitafrolov.designsystem.tools.text.stringText
import nikitafrolov.feature.exchanger.R
import org.koin.androidx.compose.getViewModel

const val exchangerRoute = "exchanger"

fun NavGraphBuilder.exchangerScreen(
    onBack: () -> Unit,
) {
    composable(exchangerRoute) {
        ExchangerScreen()
    }
}

@Composable
private fun ExchangerScreen(
    viewModel: ExchangerViewModel = getViewModel(),
) {
    val state by viewModel.state.collectAsState()

    ExchangerContent(
        state = state,
        onSellAmountChange = viewModel::onSellAmountChange,
        onPickSellAccount = viewModel::onPickSellAccount,
        onReceiveAmountChange = viewModel::onReceiveAmountChange,
        onPickReceiveAccount = viewModel::onPickReceiveAccount
    )
}

@Preview
@Composable
private fun ExchangerPreview() {
    LaunchTheme {
        ExchangerContent(ExchangerState())
    }
}

@Composable
private fun ExchangerContent(
    state: ExchangerState,
    onSellAmountChange: (TextFieldValue) -> Unit = {},
    onPickSellAccount: () -> Unit = {},
    onReceiveAmountChange: (TextFieldValue) -> Unit = {},
    onPickReceiveAccount: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Toolbar(text = stringResource(R.string.exchanger__title))

        Box {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                AmountInput(
                    title = stringResource(R.string.exchanger__sell_field_title),
                    amount = state.sell,
                    balance = state.sellBalance,
                    onAmountChange = { onSellAmountChange(it) },
                    onClick = { onPickSellAccount() }
                )

                AmountInput(
                    modifier = Modifier.padding(top = 8.dp),
                    title = stringResource(R.string.exchanger__receive_field_title),
                    amount = state.receive,
                    balance = state.receiveBalance,
                    onAmountChange = { onReceiveAmountChange(it) },
                    onClick = { onPickReceiveAccount() }
                )
            }

            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.primary)
                    .rotate(90f),
                painter = painterResource(LaunchIcons.CompareArrows),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                contentScale = ContentScale.Inside
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            modifier = Modifier.padding(24.dp),
            text = stringText(state.buttonTitle)
        )
    }
}

private const val AMOUNT_MAX_LENGTH = 13

@Composable
private fun AmountInput(
    modifier: Modifier = Modifier,
    title: String = "",
    amount: TextFieldValue = TextFieldValue(""),
    balance: String = "",
    onAmountChange: (TextFieldValue) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickableThrottle { onClick() }
            .padding(16.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = balance,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Row(verticalAlignment = Alignment.Bottom) {
            InputNumberDecimal(
                value = amount,
                onValueChange = {
                    if (it.text.length < AMOUNT_MAX_LENGTH) onAmountChange(it)
                },
                textStyle = MaterialTheme.typography.headlineLarge
                    .copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(LaunchIcons.ArrowDown),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            )
        }
    }
}