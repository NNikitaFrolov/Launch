package nikitafrolov.exchanger.data.remote.source

import nikitafrolov.exchanger.data.remote.api.CurrencyApi
import nikitafrolov.exchanger.domain.source.remote.AccountRemoteDataSource
import nikitafrolov.model.Currency
import nikitafrolov.model.result.Result
import nikitafrolov.model.result.flatMap
import nikitafrolov.model.result.map
import org.koin.core.annotation.Single
import java.math.BigDecimal

@Single
internal class AccountRemoteDataSourceImpl(
    private val api: CurrencyApi,
) : AccountRemoteDataSource {

    override suspend fun getRateByCurrency(sell: Currency, buy: Currency): Result<BigDecimal> {
        // Api support rate only for EUR currency
        return api.currencyExchangeRates().flatMap { result ->
            result.value.rates[buy.code]
                ?.let { Result.Success.Value(it) }
                ?: Result.Success.Empty
        }
    }

    override suspend fun getForBuyCurrencies(): Result<List<Currency>> {
        return api.currencyExchangeRates().map { result ->
            result.rates.keys.map(::Currency)
        }
    }

    override suspend fun getForSellCurrencies(): Result<List<Currency>> {
        // Api support rate only for EUR currency
        return Result.Success.Value(
            listOf(Currency(code = "EUR"))
        )
    }
}