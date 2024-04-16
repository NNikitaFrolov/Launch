package nikitafrolov.exchanger.data.remote.api

import io.ktor.client.HttpClient
import nikitafrolov.exchanger.data.remote.model.CurrencyRateDto
import nikitafrolov.model.result.Result
import nikitafrolov.network.result.getResult

internal class CurrencyApi(private val ktor: HttpClient) {

    suspend fun currencyExchangeRates(): Result<CurrencyRateDto> =
        ktor.getResult("tasks/api/currency-exchange-rates")
}