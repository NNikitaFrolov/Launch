package nikitafrolov.designsystem.extencion

import nikitafrolov.model.Amount

fun Amount?.format(): String {
    return if (this != null) "${value.toPlainString()} ${currency.code}" else ""
}
