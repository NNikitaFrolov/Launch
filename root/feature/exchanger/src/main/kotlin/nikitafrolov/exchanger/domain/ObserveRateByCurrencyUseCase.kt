package nikitafrolov.exchanger.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import nikitafrolov.exchanger.domain.source.remote.AccountRemoteDataSource
import nikitafrolov.model.Currency
import nikitafrolov.model.result.Result
import org.koin.core.annotation.Factory
import java.math.BigDecimal
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Factory
internal class ObserveRateByCurrencyUseCase(
    private val remoteDataSource: AccountRemoteDataSource,
) {

    companion object {
        private const val SYNC_INTERVAL_SECONDS = 5
    }

    operator fun invoke(sell: Currency, buy: Currency): Flow<Result<BigDecimal>> {
        return tickerFlow(SYNC_INTERVAL_SECONDS.seconds)
            .map { remoteDataSource.getRateByCurrency(sell, buy) }
    }

    private fun tickerFlow(period: Duration) = flow {
        while (true) {
            emit(Unit)
            delay(period)
        }
    }
}