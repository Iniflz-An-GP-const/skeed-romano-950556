package goldrush.skeedromano.official.ui.composable.screen.shared.modifier.press

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import goldrush.skeedromano.official.ui.composable.screen.shared.modifier.press.rememberPressProgress

private const val PRESSED_SCALE_X = 1.02f
private const val PRESSED_SCALE_Y = 0.94f

/**
 * The element is squeezed while pressed — a bit lower and a bit wider, like under a finger —
 * and wobbles back to its shape on release.
 */
@Composable
fun Modifier.pressEffect(interactionSource: InteractionSource): Modifier {
    val progress = rememberPressProgress(interactionSource)

    return graphicsLayer {
        scaleX = 1f + progress.value * (PRESSED_SCALE_X - 1f)
        scaleY = 1f - progress.value * (1f - PRESSED_SCALE_Y)
    }
}
