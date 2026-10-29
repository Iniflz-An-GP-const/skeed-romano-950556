package goldrush.skeedromano.official.ui.composable.screen.game.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import goldrush.skeedromano.official.ui.composable.screen.game.timer.TimerIndicatorItem
import kotlin.math.ceil

private const val SegmentCount = 10
private val SegmentHeight = 8.dp
private val SegmentSpacing = 4.dp

/** A row of ticks fills in whole steps instead of smoothly — a more "game show" read than a smooth bar. */
@Composable
fun TimerIndicator(
    item: TimerIndicatorItem,
    modifier: Modifier = Modifier,
) {
    val filledCount = ceil(item.progress * SegmentCount).toInt().coerceIn(0, SegmentCount)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SegmentSpacing),
    ) {
        repeat(SegmentCount) { index ->
            val filled = index < filledCount

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(SegmentHeight)
                    .clip(RoundedCornerShape(SegmentHeight / 2))
                    .background(
                        if (filled) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outlineVariant
                        },
                    ),
            )
        }
    }
}
