package nikitafrolov.designsystem.tools

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

private const val CLICK_THROTTLE_TIME = 300L

@Immutable
class ClickThrottle {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0
    fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= CLICK_THROTTLE_TIME) {
            event.invoke()
            lastEventTimeMs = now
        }
    }

    companion object
}

fun Modifier.clickableThrottle(
    enabled: Boolean = true,
    clickThrottle: ClickThrottle? = null,
    onClick: () -> Unit,
) = composed {
    val rememberClickThrottle = clickThrottle ?: remember { ClickThrottle() }
    Modifier.clickable(
        enabled = enabled,
        onClick = { rememberClickThrottle.processEvent { onClick() } },
    )
}

fun Modifier.clickableThrottle(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource,
    indication: Indication? = null,
    clickThrottle: ClickThrottle? = null,
    onClick: () -> Unit,
) = composed {
    val rememberClickThrottle = clickThrottle ?: remember { ClickThrottle() }
    Modifier.clickable(
        enabled = enabled,
        onClick = { rememberClickThrottle.processEvent { onClick() } },
        indication = indication,
        interactionSource = interactionSource
    )
}