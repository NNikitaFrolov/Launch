package nikitafrolov.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import nikitafrolov.designsystem.extencion.thenIf
import nikitafrolov.designsystem.icon.LaunchIcons
import nikitafrolov.designsystem.theme.LaunchTheme
import nikitafrolov.designsystem.tools.clickableThrottle

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    isFillMaxWidth: Boolean = true,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = when {
        !enabled -> MaterialTheme.colorScheme.secondaryContainer
        isPressed && !isLoading -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.primary
    }

    ConstraintLayout(
        modifier = modifier
            .thenIf(isFillMaxWidth) { fillMaxWidth() }
            .clip(MaterialTheme.shapes.extraLarge)
            .background(backgroundColor)
            .clickableThrottle(
                interactionSource = interactionSource,
                indication = null,
                onClick = { if (enabled && !isLoading) onClick() },
            )
    ) {
        val (iconRef, textRef, progressRef) = createRefs()

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .constrainAs(progressRef) {
                        centerTo(parent)
                    },
                color = MaterialTheme.colorScheme.onPrimary,
            )
        } else {
            val textPadding = if (icon != null) {
                val color = MaterialTheme.colorScheme
                val iconColor = if (enabled) color.onPrimary else color.secondary

                Image(
                    painter = icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(iconColor),
                    modifier = Modifier
                        .constrainAs(iconRef) {
                            centerVerticallyTo(parent)
                            start.linkTo(parent.start, 24.dp)
                        }
                )
                PaddingValues(horizontal = 60.dp, vertical = 16.dp)
            } else {
                PaddingValues(horizontal = 24.dp, vertical = 16.dp)
            }

            val textColor = if (enabled) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.secondary
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(textPadding)
                    .constrainAs(textRef) {
                        centerTo(parent)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    LaunchTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        ) {
            PrimaryButton(
                text = "Button",
                modifier = Modifier.padding(20.dp),
            )
            PrimaryButton(
                text = "Button",
                icon = painterResource(id = LaunchIcons.AttachMoney),
                modifier = Modifier.padding(20.dp),
            )
            PrimaryButton(
                text = "Button",
                enabled = false,
                modifier = Modifier.padding(20.dp),
            )
            PrimaryButton(
                text = "Button",
                icon = painterResource(id = LaunchIcons.AttachMoney),
                enabled = false,
                modifier = Modifier.padding(20.dp),
            )
            PrimaryButton(
                text = "Button",
                icon = painterResource(id = LaunchIcons.AttachMoney),
                isLoading = true,
                modifier = Modifier.padding(20.dp),
            )
        }
    }
}
