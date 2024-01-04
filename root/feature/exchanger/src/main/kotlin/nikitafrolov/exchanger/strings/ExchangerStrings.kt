package nikitafrolov.exchanger.strings

internal data class ExchangerStrings(
    val title: String,
    val sellFieldTitle: String,
    val receiveFieldTitle: String,
    val exchangeMessagePattern: (sell: String, buy: String, fee: String) -> String
)