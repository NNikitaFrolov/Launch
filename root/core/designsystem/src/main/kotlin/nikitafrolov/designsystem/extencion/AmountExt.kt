package nikitafrolov.designsystem.extencion

import nikitafrolov.designsystem.component.numberFormatter
import nikitafrolov.model.Amount

fun Amount?.format(): String {
    return if (this != null) "${numberFormatter.format(value)} ${currency.code}" else ""
}
