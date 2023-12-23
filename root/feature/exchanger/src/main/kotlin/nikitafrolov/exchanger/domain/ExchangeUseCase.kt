package nikitafrolov.exchanger.domain

import nikitafrolov.exchanger.domain.source.local.AccountLocalDataSource
import nikitafrolov.exchanger.domain.source.remote.AccountRemoteDataSource
import nikitafrolov.model.Account
import nikitafrolov.model.result.Result
import nikitafrolov.model.result.asFailure
import nikitafrolov.model.result.flatMap
import nikitafrolov.model.result.isSuccess
import org.koin.core.annotation.Factory
import java.math.BigDecimal

@Factory
internal class ExchangeUseCase(
    private val remoteDataSource: AccountRemoteDataSource,
    private val localDataSource: AccountLocalDataSource,
) {
    suspend operator fun invoke(
        sell: Account,
        buy: Account,
        sellAmount: BigDecimal
    ): Result<Triple<Account, Account, BigDecimal>> {
        val exchangeResult = remoteDataSource.exchange(sell, buy, sellAmount)

        return if (exchangeResult.isSuccess()) {
            val (updatedSell, updatedBuy, _) = exchangeResult.value
            localDataSource.updateAccounts(
                mapOf(sell.currency to updatedSell, buy.currency to updatedBuy)
            ).flatMap { exchangeResult }
        } else {
            exchangeResult.asFailure()
        }
    }

}