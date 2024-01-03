package nikitafrolov.exchanger.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import nikitafrolov.designsystem.component.InputNumberDecimal
import nikitafrolov.designsystem.component.PrimaryButton
import nikitafrolov.designsystem.component.Toolbar
import nikitafrolov.designsystem.extencion.format
import nikitafrolov.designsystem.icon.LaunchIcons
import nikitafrolov.designsystem.theme.LaunchTheme
import nikitafrolov.designsystem.tools.ClickThrottle
import nikitafrolov.designsystem.tools.clickableThrottle
import nikitafrolov.designsystem.tools.text.stringText
import nikitafrolov.feature.exchanger.R
import nikitafrolov.feature.exchanger.strings
import nikitafrolov.model.Account
import org.koin.androidx.compose.getViewModel

const val exchangerRoute = "exchanger"

fun NavGraphBuilder.exchangerScreen() {
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
        onPickReceiveAccount = viewModel::onPickReceiveAccount,
        onAccountPick = viewModel::onAccountPick,
        onAccountPickerDismiss = viewModel::onAccountPickerDismiss,
        onSubmitClick = viewModel::onSubmitClick,
        onNotifierDismiss = viewModel::onNotifierDismiss,
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
    onPickReceiveAccount: () -> Unit = {},
    onAccountPick: (Account, Boolean) -> Unit = { _, _ -> },
    onAccountPickerDismiss: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onNotifierDismiss: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Toolbar(text = strings.title)

        Box {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                AmountInput(
                    readOnly = state.sellAccount == null || state.receiveAccount == null,
                    title = stringResource(R.string.exchanger__sell_field_title),
                    amount = state.sell,
                    balance = state.sellAccount?.balanceAmount.format(),
                    onAmountChange = { onSellAmountChange(it) },
                    onClick = { onPickSellAccount() }
                )

                AmountInput(
                    modifier = Modifier.padding(top = 8.dp),
                    readOnly = true,
                    title = stringResource(R.string.exchanger__receive_field_title),
                    amount = state.receive,
                    balance = state.receiveAccount?.balanceAmount.format(),
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
            text = stringText(state.buttonTitle),
            isLoading = state.isLoading,
            enabled = state.submitEnabled,
            onClick = onSubmitClick
        )

        AccountPickerBottomSheet(
            state = state.accountPickerState,
            onAccountPick = onAccountPick,
            onDismiss = onAccountPickerDismiss,
        )

        NotifyBottomSheet(
            state = state.notifierState,
            onDismiss = onNotifierDismiss
        )
    }
}

private const val AMOUNT_MAX_LENGTH = 8

@Composable
private fun AmountInput(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
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
                readOnly = readOnly,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountPickerBottomSheet(
    state: AccountPickerState,
    onAccountPick: (Account, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    if (state.isShow) {
        val clickThrottle = remember { ClickThrottle() }
        val modalBottomSheetState = rememberModalBottomSheetState()

        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            LazyColumn {
                items(state.accounts) { account ->
                    Surface(
                        onClick = {
                            clickThrottle.processEvent { onAccountPick(account, state.isSell) }
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp, horizontal = 24.dp),
                            text = account.currency.unit + account.balanceAmount.format(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotifyBottomSheet(
    state: NotifierState,
    onDismiss: () -> Unit
) {
    if (state.isShow) {
        val modalBottomSheetState = rememberModalBottomSheetState()

        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(24.dp),
                        text = stringText(state.message),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                    )
                }

                PrimaryButton(
                    modifier = Modifier.padding(24.dp),
                    text = stringResource(id = R.string.exchanger__button_ok),
                    onClick = onDismiss
                )
            }
        }
    }
}