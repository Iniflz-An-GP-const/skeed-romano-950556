package goldrush.skeedromano.official.ui.composable.screen.game_over.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.ui.composable.screen.game_over.player.PlayerScoreItem
import goldrush.skeedromano.official.ui.theme.LocalDimens

private val CrownIconSize = 24.dp

/** Card chrome never changes — only the winner gets a trophy badge above their score. */
@Composable
fun PlayerScoreCard(
    item: PlayerScoreItem,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val shape = RoundedCornerShape(dimens.cardCornerRadius)

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = shape)
            .padding(dimens.cardPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimens.tightSpacing),
    ) {
        Box(modifier = Modifier.size(CrownIconSize)) {
            if (item.isWinner) {
                Icon(
                    imageVector = Icons.Filled.EmojiEvents,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(CrownIconSize),
                )
            }
        }

        Text(
            text = stringResource(R.string.game_over_player_format, item.playerNumber),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = "${item.correctAnswers}/${item.totalQuestions}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
