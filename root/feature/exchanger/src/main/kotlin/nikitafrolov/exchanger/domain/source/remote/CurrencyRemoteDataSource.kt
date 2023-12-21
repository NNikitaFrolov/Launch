package nikitafrolov.exchanger.domain.source.remote

import nikitafrolov.network.result.Result
import java.math.BigDecimal

internal interface CurrencyRemoteDataSource {

    suspend fun observeRates(): Result<Map<String, BigDecimal>>

}