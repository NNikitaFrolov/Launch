package nikitafrolov.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import nikitafrolov.designsystem.theme.LaunchTheme
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

private const val FractionMin = 0
private const val FractionMax = 2
private const val GroupSize = 3
private const val SeparatorDecimal = '.'
private const val SeparatorGroup = ' '

internal val numberFormatter: DecimalFormat = DecimalFormat("#,###.##").apply {
    maximumFractionDigits = FractionMax
    minimumFractionDigits = FractionMin
    isGroupingUsed = true
    groupingSize = GroupSize
    roundingMode = RoundingMode.HALF_UP

    decimalFormatSymbols = DecimalFormatSymbols().apply {
        groupingSeparator = SeparatorGroup
        decimalSeparator = SeparatorDecimal
    }
}

private val decimalInputFilter by lazy { Regex("^(\\d*\\.?)+\$") }

@Composable
fun InputNumberDecimal(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    onValueChange: (value: TextFieldValue) -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    readOnly: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        modifier = modifier,
        value = filteredDecimal(value),
        onValueChange = {
            if (it.text.contains(decimalInputFilter)) onValueChange(filteredDecimal(it))
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
        ),
        readOnly = readOnly,
        singleLine = true,
        textStyle = textStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        interactionSource = interactionSource,
        visualTransformation = DecimalAmountTransformation(),
    )
}

@Preview(showBackground = true)
@Composable
private fun InputNumberPreview() {
    LaunchTheme {
        Box {
            InputNumberDecimal(value = TextFieldValue("150.32"))
        }
    }
}

private class DecimalAmountTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val transformation = reformat(text.text)

        return TransformedText(
            AnnotatedString(transformation.formatted),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return transformation.originalToTransformed[offset]
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return transformation.transformedToOriginal[offset]
                }
            },
        )
    }

    private fun reformat(original: String): Transformation {
        val parts = original.split(SeparatorDecimal)
        check(parts.size < GroupSize) { "original text must have only one dot" }

        val hasEndDot = original.endsWith(SeparatorDecimal)
        val formatted = when {
            original.isNotEmpty() && parts.size == 1 -> {
                numberFormatter.format(parts[0].toBigDecimalOrNull() ?: 0).let {
                    if (hasEndDot) it + SeparatorDecimal else it
                }
            }

            parts.size == 2 -> {
                val numberPart = numberFormatter.format(parts[0].toBigDecimalOrNull() ?: 0)
                val decimalPart = parts[1]

                "$numberPart.$decimalPart"
            }

            else -> original
        }

        val originalToTransformed = mutableListOf<Int>()
        val transformedToOriginal = mutableListOf<Int>()
        var specialCharsCount = 0

        formatted.forEachIndexed { index, char ->
            if (SeparatorGroup == char) {
                specialCharsCount++
            } else {
                originalToTransformed.add(index)
            }
            transformedToOriginal.add(index - specialCharsCount)
        }
        originalToTransformed.add(originalToTransformed.maxOrNull()?.plus(1) ?: 0)
        transformedToOriginal.add(transformedToOriginal.maxOrNull()?.plus(1) ?: 0)

        return Transformation(formatted, originalToTransformed, transformedToOriginal)
    }
}

private fun filteredDecimal(input: TextFieldValue): TextFieldValue {
    var inputText = input.text.replaceFirst(regex = Regex("^0+(?!$)"), "")
    val startsWithDot = input.text.startsWith(SeparatorDecimal)

    var selectionStart = input.selection.start
    var selectionEnd = input.selection.end

    if (startsWithDot) {
        inputText = "0$inputText"

        if (selectionStart == selectionEnd) {
            selectionStart++
            selectionEnd++
        } else {
            selectionEnd++
        }
    }

    val parts = inputText.split(SeparatorDecimal)
    val text = if (parts.size > 1) {
        parts[0] + SeparatorDecimal + parts.subList(1, parts.size)
            .joinToString("").take(2)
    } else {
        parts.joinToString("")
    }
    return TextFieldValue(
        if (text.startsWith(SeparatorDecimal)) "0$text" else text,
        TextRange(selectionStart, selectionEnd)
    )
}

private data class Transformation(
    val formatted: String,
    val originalToTransformed: List<Int>,
    val transformedToOriginal: List<Int>,
)