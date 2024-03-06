package nikitafrolov.exchanger.data.remote.api

import nikitafrolov.exchanger.data.remote.model.CurrencyRateDto
import nikitafrolov.model.result.Result
import retrofit2.http.GET

internal interface CurrencyApi {

    @GET("tasks/api/currency-exchange-rates")
    suspend fun currencyExchangeRates(): Result<CurrencyRateDto>
}