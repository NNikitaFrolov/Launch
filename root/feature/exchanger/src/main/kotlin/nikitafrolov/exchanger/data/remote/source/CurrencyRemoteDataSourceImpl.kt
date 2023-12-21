package nikitafrolov.exchanger.data.remote.source

import nikitafrolov.exchanger.data.remote.api.CurrencyApi
import nikitafrolov.exchanger.domain.source.remote.CurrencyRemoteDataSource
import nikitafrolov.network.result.Result
import nikitafrolov.network.result.asFailure
import nikitafrolov.network.result.isSuccess
import nikitafrolov.network.result.map
import org.koin.core.annotation.Factory
import java.math.BigDecimal

@Factory
internal class CurrencyRemoteDataSourceImpl(
    private val api: CurrencyApi,
) : CurrencyRemoteDataSource {

    override suspend fun observeRates(): Result<Map<String, BigDecimal>> {
        return api.currencyExchangeRates().let { result ->
            if (result.isSuccess()) result.map { it.rates } else result.asFailure()
        }
    }
}