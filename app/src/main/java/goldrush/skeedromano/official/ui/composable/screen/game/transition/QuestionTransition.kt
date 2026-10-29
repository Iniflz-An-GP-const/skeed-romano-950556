package goldrush.skeedromano.official.ui.composable.screen.game.transition

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import goldrush.skeedromano.official.ui.composable.screen.game.transition.QuestionTransitionDefaults

/** The answered question fades out first, then the next one fades in on the same spot. */
@Composable
fun QuestionTransition(
    questionIndex: Int,
    modifier: Modifier = Modifier,
    content: @Composable (questionIndex: Int) -> Unit,
) {
    val halfDurationMs = QuestionTransitionDefaults.DURATION_MS / 2

    AnimatedContent(
        targetState = questionIndex,
        transitionSpec = {
            fadeIn(tween(durationMillis = halfDurationMs, delayMillis = halfDurationMs))
                .togetherWith(fadeOut(tween(durationMillis = halfDurationMs)))
        },
        label = "QuestionTransition",
        modifier = modifier,
    ) { index ->
        content(index)
    }
}
