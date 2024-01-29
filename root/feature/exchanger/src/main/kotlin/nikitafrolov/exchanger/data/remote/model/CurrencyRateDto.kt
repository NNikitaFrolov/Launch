package nikitafrolov.exchanger.data.remote.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import nikitafrolov.network.converter.BigDecimalSerializable

@Serializable
internal data class CurrencyRateDto(
    val base: String,
    val date: LocalDate,
    val rates: Map<String, BigDecimalSerializable>,
)
