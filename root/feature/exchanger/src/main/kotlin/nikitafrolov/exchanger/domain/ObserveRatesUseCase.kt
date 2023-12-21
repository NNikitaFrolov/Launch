package nikitafrolov.exchanger.domain

import nikitafrolov.exchanger.domain.source.remote.CurrencyRemoteDataSource
import nikitafrolov.network.result.Result
import org.koin.core.annotation.Factory
import java.math.BigDecimal

@Factory
internal class ObserveRatesUseCase(
    private val dataSource: CurrencyRemoteDataSource
) {

    suspend operator fun invoke(): Result<Map<String, BigDecimal>> {
        return dataSource.observeRates()
    }
}