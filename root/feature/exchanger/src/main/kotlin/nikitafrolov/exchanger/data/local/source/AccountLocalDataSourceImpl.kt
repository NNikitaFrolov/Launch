package nikitafrolov.exchanger.data.local.source

import nikitafrolov.exchanger.domain.source.local.AccountLocalDataSource
import nikitafrolov.model.Account
import nikitafrolov.model.Currency
import nikitafrolov.model.result.Result
import nikitafrolov.model.result.ResultEmpty
import org.koin.core.annotation.Single
import java.math.BigDecimal

@Single
internal class AccountLocalDataSourceImpl : AccountLocalDataSource {

    private val accounts = mutableMapOf(
        Currency("EUR") to Account(Currency("EUR"), BigDecimal(1000))
    )

    override suspend fun getAccounts(): Result<Map<Currency, Account>> {
        return Result.Success.Value(accounts)
    }

    override suspend fun updateAccounts(newAccounts: Map<Currency, Account>): ResultEmpty {
        accounts.putAll(newAccounts)
        return Result.Success.Empty
    }
}