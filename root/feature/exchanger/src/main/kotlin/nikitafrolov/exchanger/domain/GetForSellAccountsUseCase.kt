package nikitafrolov.exchanger.domain

import nikitafrolov.exchanger.domain.source.local.AccountLocalDataSource
import nikitafrolov.exchanger.domain.source.remote.AccountRemoteDataSource
import nikitafrolov.model.Account
import nikitafrolov.model.result.Result
import nikitafrolov.model.result.asFailure
import nikitafrolov.model.result.isFailure
import nikitafrolov.model.result.isSuccess
import nikitafrolov.model.result.map
import org.koin.core.annotation.Factory

@Factory
internal class GetForSellAccountsUseCase(
    private val remoteDataSource: AccountRemoteDataSource,
    private val localDataSource: AccountLocalDataSource
) {
    suspend operator fun invoke(): Result<List<Account>> {
        val resultAccount = localDataSource.getAccounts()
        val resultCurrencies = remoteDataSource.getForSellCurrencies()

        return if (resultAccount.isSuccess() && resultCurrencies.isSuccess()) {
            resultCurrencies.map { currencies ->
                currencies.mapNotNull { resultAccount.value[it] }
            }
        } else if (resultAccount.isFailure()) {
            resultAccount.asFailure()
        } else {
            resultCurrencies.asFailure()
        }
    }
}