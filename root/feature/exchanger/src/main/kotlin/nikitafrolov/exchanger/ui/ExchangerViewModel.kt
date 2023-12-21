package nikitafrolov.exchanger.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import nikitafrolov.designsystem.tools.text.Text
import nikitafrolov.feature.exchanger.R
import org.koin.android.annotation.KoinViewModel

@Immutable
internal data class ExchangerState(
    val sell: TextFieldValue = TextFieldValue(),
    val sellBalance: String = "1000 USD", //TODO create Amount
    val receive: TextFieldValue = TextFieldValue(),
    val receiveBalance: String = "900 EUR", //TODO create Amount
    val buttonTitle: Text = Text.Res(R.string.exchanger__button_submit)
)

@KoinViewModel
internal class ExchangerViewModel : ViewModel() {

    val state = MutableStateFlow(ExchangerState())

    fun onSellAmountChange(field: TextFieldValue) {
        //TODO
        state.update { it.copy(sell = field) }
    }

    fun onReceiveAmountChange(field: TextFieldValue) {
        //TODO
        state.update { it.copy(receive = field) }
    }

    fun onPickSellAccount() {
        //TODO
    }

    fun onPickReceiveAccount() {
        //TODO
    }
}