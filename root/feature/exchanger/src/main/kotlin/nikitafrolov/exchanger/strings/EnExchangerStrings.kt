package nikitafrolov.exchanger.strings

import cafe.adriel.lyricist.LyricistStrings
import nikitafrolov.model.Locales

@LyricistStrings(languageTag = Locales.EN, default = true)
internal val EnExchangerStrings = ExchangerStrings(
    title = "Currency converter",
    sellFieldTitle = "Sell",
    receiveFieldTitle = "Receive",
    exchangeMessagePattern = { sell: String, buy: String, fee: String ->
        "You have converted $sell to $buy. Commission Fee - $fee."
    }
)