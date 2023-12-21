package nikitafrolov.designsystem.tools.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

@SuppressWarnings("SpreadOperator")
@Composable
@ReadOnlyComposable
fun stringText(text: Text): String = when (text) {
    is Text.Res -> stringResource(id = text.resId)
    is Text.ResParams -> stringResource(id = text.value, *text.args.toTypedArray())
    is Text.Str -> text.text
}
