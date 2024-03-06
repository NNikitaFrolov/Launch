package nikitafrolov.exchanger.domain

import nikitafrolov.exchanger.domain.source.local.AccountLocalDataSource
import nikitafrolov.exchanger.domain.source.remote.AccountRemoteDataSource
import nikitafrolov.model.Account
import nikitafrolov.model.result.Result
import nikitafrolov.model.result.asFailure
import nikitafrolov.model.result.isFailure
import nikitafrolov.model.result.isSuccess
import org.koin.core.annotation.Factory
import java.math.BigDecimal

@Factory
internal class GetForBuyAccountsUseCase(
    private val remoteDataSource: AccountRemoteDataSource,
    private val localDataSource: AccountLocalDataSource
) {
    suspend operator fun invoke(sellAccount: Account): Result<List<Account>> {
        val resultAccount = localDataSource.getAccounts()
        val resultCurrencies = remoteDataSource.getForBuyCurrencies()

        return if (resultAccount.isSuccess() && resultCurrencies.isSuccess()) {
            val updateAccount = resultCurrencies.value
                .associateWith { resultAccount.value[it] ?: Account(it, BigDecimal.ZERO) }

            localDataSource.updateAccounts(updateAccount)

            updateAccount.mapNotNull { (currency, account) ->
                if (sellAccount.currency.code != currency.code) account else null
            }.let {
                Result.Success.Value(it)
            }
        } else if (resultAccount.isFailure()) {
            resultAccount.asFailure()
        } else {
            resultCurrencies.asFailure()
        }
    }
}