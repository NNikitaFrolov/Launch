package nikitafrolov.model

import java.math.BigDecimal

data class Account(
    val currency: Currency,
    val balance: BigDecimal,
) {
    val balanceAmount: Amount = Amount(balance, currency)
}
