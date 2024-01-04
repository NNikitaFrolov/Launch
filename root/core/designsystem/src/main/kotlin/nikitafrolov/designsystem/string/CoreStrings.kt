package nikitafrolov.designsystem.string

import androidx.compose.runtime.Composable
import nikitafrolov.core.designsystem.LocalDesignsystemStrings

val coreStrings: CoreStrings
    @Composable
    get() = LocalDesignsystemStrings.current

data class CoreStrings(
    val appName: String,
    val buttonString: ButtonStrings,
)

data class ButtonStrings(
    val ok: String,
    val submit: String,
)