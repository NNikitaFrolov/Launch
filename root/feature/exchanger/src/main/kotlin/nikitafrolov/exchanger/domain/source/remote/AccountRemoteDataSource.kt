package nikitafrolov.exchanger.domain.source.remote

import nikitafrolov.model.Account
import nikitafrolov.model.Currency
import nikitafrolov.model.result.Result
import java.math.BigDecimal

internal interface AccountRemoteDataSource {

    suspend fun getRateByCurrency(sell: Currency, buy: Currency): Result<BigDecimal>

    suspend fun getForBuyCurrencies(): Result<List<Currency>>

    suspend fun getForSellCurrencies(): Result<List<Currency>>

    suspend fun exchange(
        sell: Account,
        buy: Account,
        sellAmount: BigDecimal
    ): Result<Triple<Account, Account, BigDecimal>>

}