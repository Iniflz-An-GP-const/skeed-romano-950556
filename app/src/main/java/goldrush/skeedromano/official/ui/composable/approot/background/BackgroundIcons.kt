package goldrush.skeedromano.official.ui.composable.approot.background

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SportsBaseball
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.filled.SportsFootball
import androidx.compose.material.icons.filled.SportsGolf
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.filled.SportsHockey
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material.icons.filled.SportsVolleyball
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

val BackgroundIconPool: List<ImageVector> = listOf(
    Icons.Filled.SportsSoccer,
    Icons.Filled.SportsBasketball,
    Icons.Filled.SportsTennis,
    Icons.Filled.SportsFootball,
    Icons.Filled.SportsVolleyball,
    Icons.Filled.SportsBaseball,
    Icons.Filled.SportsHockey,
    Icons.Filled.SportsGolf,
    Icons.Filled.SportsCricket,
    Icons.Filled.SportsMma,
    Icons.Filled.SportsHandball,
    Icons.Filled.SportsScore,
    Icons.Filled.EmojiEvents,
    Icons.Filled.FitnessCenter,
    Icons.AutoMirrored.Filled.DirectionsRun,
    Icons.AutoMirrored.Filled.DirectionsBike,
    Icons.Filled.Pool,
    Icons.Filled.Timer,
)

data class PlacedIcon(
    val icon: ImageVector,
    val anchor: BiasAlignment,
    val size: Dp,
    val baseAlpha: Float,
)

/**
 * Pairs [anchors] (from one `points/v_*` layout) with distinct icons from
 * [BackgroundIconPool]. [anchors] is expected to be no larger than the icon pool —
 * that's what keeps every icon on screen at once different.
 */
fun placeBackgroundIcons(random: Random, anchors: List<BiasAlignment>): List<PlacedIcon> {
    val iconsForAnchors = buildList {
        while (size < anchors.size) {
            addAll(BackgroundIconPool.shuffled(random))
        }
    }.take(anchors.size)

    return anchors.mapIndexed { index, anchor ->
        PlacedIcon(
            icon = iconsForAnchors[index],
            anchor = anchor,
            size = (28 + random.nextInt(20)).dp,
            baseAlpha = 0.09f + random.nextFloat() * 0.08f,
        )
    }
}
