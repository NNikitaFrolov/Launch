package nikitafrolov.exchanger.domain.source.local

import nikitafrolov.model.Account
import nikitafrolov.model.Currency
import nikitafrolov.model.result.Result
import nikitafrolov.model.result.ResultEmpty

internal interface AccountLocalDataSource {

    suspend fun getAccounts(): Result<Map<Currency, Account>>

    suspend fun updateAccounts(newAccounts: Map<Currency, Account>): ResultEmpty

}