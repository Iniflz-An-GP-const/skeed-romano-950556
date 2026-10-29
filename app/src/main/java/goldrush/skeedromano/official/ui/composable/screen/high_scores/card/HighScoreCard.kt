package goldrush.skeedromano.official.ui.composable.screen.high_scores.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.ui.composable.screen.high_scores.card.HighScoreCardDefaults
import goldrush.skeedromano.official.ui.composable.screen.high_scores.card.HighScoreCardItem
import goldrush.skeedromano.official.ui.theme.LocalDimens
import goldrush.skeedromano.official.ui.theme.cardBorderStroke

private val BadgeSize = 48.dp

/** Accuracy percentage in a circular badge, leading the score line and date. */
@Composable
fun HighScoreCard(
    item: HighScoreCardItem,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val shape = HighScoreCardDefaults.shape
    val border = cardBorderStroke()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = shape)
            .let { base -> if (border != null) base.border(border, shape) else base }
            .padding(HighScoreCardDefaults.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        Box(
            modifier = Modifier
                .size(BadgeSize)
                .background(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.high_score_card_percent_format, item.accuracyPercent),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(dimens.tightSpacing)) {
            Text(
                text = stringResource(R.string.high_score_card_score_format, item.correctAnswers, item.totalQuestions),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = item.formattedDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
