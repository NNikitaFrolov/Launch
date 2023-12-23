package nikitafrolov.exchanger.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
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

@Immutable
internal data class ExchangerState(
    val sell: TextFieldValue = TextFieldValue(),
    val sellAccount: Account? = null,
    val receive: TextFieldValue = TextFieldValue(),
    val receiveAccount: Account? = null,
    val buttonTitle: Text = Text.Res(R.string.exchanger__button_submit),
    val isLoading: Boolean = false,
    val accountPickerState: AccountPickerState = AccountPickerState(),
)

@Immutable
internal data class AccountPickerState(
    val isShow: Boolean = false,
    val isSell: Boolean = true,
    val accounts: List<Account> = listOf(),
)

@KoinViewModel
internal class ExchangerViewModel(
    private val observeRateByCurrency: ObserveRateByCurrencyUseCase,
    private val getForSellAccounts: GetForSellAccountsUseCase,
    private val getForBuyAccounts: GetForBuyAccountsUseCase,
) : ViewModel() {

    val state = MutableStateFlow(ExchangerState())

    init {
        viewModelScope.launch {
            state.mapNotNull {
                if (it.sellAccount != null && it.receiveAccount != null) {
                    Triple(it.sell, it.sellAccount, it.receiveAccount)
                } else {
                    null
                }
            }.flatMapLatest { (sellField, sellAccount, receiveAccount) ->
                observeRateByCurrency(sellAccount.currency, receiveAccount.currency)
                    .map { rate ->
                        val amount = sellField.text.toBigDecimalOrNull()
                        if (rate.isSuccess() && amount != null) {
                            rate.value.times(amount).toString()
                        } else {
                            ""
                        }
                    }
            }.onEach { receive ->
                state.update { it.copy(receive = TextFieldValue(receive)) }
            }.collect()
        }
    }

    fun onSellAmountChange(field: TextFieldValue) {
        state.update { it.copy(sell = field) }
    }

    fun onPickSellAccount() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            getForSellAccounts().call { result ->
                val accountPickerState = AccountPickerState(
                    isShow = true,
                    isSell = true,
                    accounts = result.value
                )
                state.update { it.copy(accountPickerState = accountPickerState) }
            }

            state.update { it.copy(isLoading = false) }
        }
    }

    fun onPickReceiveAccount() {
        state.value.sellAccount?.let { sellAccount ->
            viewModelScope.launch {
                state.update { it.copy(isLoading = true) }

                getForBuyAccounts(sellAccount).call { result ->
                    val accountPickerState = AccountPickerState(
                        isShow = true,
                        isSell = false,
                        accounts = result.value
                    )
                    state.update { it.copy(accountPickerState = accountPickerState) }
                }

                state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onAccountPick(account: Account, isSell: Boolean) {
        state.update {
            if (isSell) {
                it.copy(sellAccount = account, accountPickerState = AccountPickerState())
            } else {
                it.copy(receiveAccount = account, accountPickerState = AccountPickerState())
            }
        }
    }

    fun onAccountPickerDismiss() {
        state.update { it.copy(accountPickerState = AccountPickerState()) }
    }
}