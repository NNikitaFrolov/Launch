package nikitafrolov.exchanger.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nikitafrolov.designsystem.tools.text.Text
import nikitafrolov.exchanger.domain.GetForBuyAccountsUseCase
import nikitafrolov.exchanger.domain.GetForSellAccountsUseCase
import nikitafrolov.exchanger.domain.ObserveRateByCurrencyUseCase
import nikitafrolov.feature.exchanger.R
import nikitafrolov.model.Account
import nikitafrolov.model.result.call
import nikitafrolov.model.result.isSuccess
import org.koin.android.annotation.KoinViewModel
import java.math.BigDecimal

@Immutable
internal data class ExchangerState(
    val sell: TextFieldValue = TextFieldValue(),
    val sellAccount: Account? = null,
    val receive: TextFieldValue = TextFieldValue(),
    val receiveAccount: Account? = null,
    val buttonTitle: Text = Text.Res(R.string.exchanger__button_submit),
    val isLoading: Boolean = false,
)

@KoinViewModel
internal class ExchangerViewModel(
    private val observeRateByCurrency: ObserveRateByCurrencyUseCase,
    private val getForSellAccounts: GetForSellAccountsUseCase,
    private val getForBuyAccounts: GetForBuyAccountsUseCase,
) : ViewModel() {

    private val sellAmountFlow = MutableStateFlow<BigDecimal?>(null)

    val state = MutableStateFlow(ExchangerState())

    init {
        //TODO need implement account picker
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            val sellAccountResult = getForSellAccounts()
            if (sellAccountResult.isSuccess()) {
                val sellAccount = sellAccountResult.value.firstOrNull()
                if (sellAccount != null) {
                    getForBuyAccounts(sellAccount).call { buyResult ->
                        buyResult.value.firstOrNull()?.let { buyAccount ->
                            state.update {
                                it.copy(sellAccount = sellAccount, receiveAccount = buyAccount)
                            }
                        }
                    }
                }
            }

            state.update { it.copy(isLoading = false) }
        }


        viewModelScope.launch {
            combine(
                state.map { it.sellAccount },
                state.map { it.receiveAccount },
            ) { sellAccount, receiveAccount ->
                sellAccount to receiveAccount
            }.mapNotNull { (sellAccount, receiveAccount) ->
                if (sellAccount != null && receiveAccount != null) {
                    sellAccount to receiveAccount
                } else {
                    null
                }
            }.flatMapConcat { (sellAccount, receiveAccount) ->
                observeRateByCurrency(sellAccount.currency, receiveAccount.currency)
            }.combine(sellAmountFlow) { rate, sellAmount ->
                val receive = if (rate.isSuccess() && sellAmount != null) {
                    rate.value.times(sellAmount).toString()
                } else {
                    ""
                }
                state.update { it.copy(receive = TextFieldValue(receive)) }
            }.collect()
        }
    }

    fun onSellAmountChange(field: TextFieldValue) {
        state.update { it.copy(sell = field) }
        field.text.toBigDecimalOrNull()?.let {
            sellAmountFlow.value = it
        } ?: let { sellAmountFlow.value = null }
    }

    fun onPickSellAccount() {
        //TODO
    }

    fun onPickReceiveAccount() {
        //TODO
    }
}