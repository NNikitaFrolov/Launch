package nikitafrolov.model

import java.math.BigDecimal

data class Amount(
    val value: BigDecimal,
    val currency: Currency,
)