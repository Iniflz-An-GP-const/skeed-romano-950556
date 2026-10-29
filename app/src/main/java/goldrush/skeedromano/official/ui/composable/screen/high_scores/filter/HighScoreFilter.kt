package goldrush.skeedromano.official.ui.composable.screen.high_scores.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.data.model.HighScoreSortType
import goldrush.skeedromano.official.ui.composable.screen.high_scores.filter.HighScoreFilterItem
import goldrush.skeedromano.official.ui.composable.screen.high_scores.filter.chip.HighScoreFilterChip
import goldrush.skeedromano.official.ui.theme.LocalDimens
import goldrush.skeedromano.official.ui.theme.cardBorderStroke

/** Chips sit inside their own panel — a tinted, bordered surface, in a scrolling row with edge padding. */
@Composable
fun HighScoreFilter(
    selected: HighScoreSortType,
    onSelect: (HighScoreSortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val shape = RoundedCornerShape(dimens.cardCornerRadius)

    val chips = listOf(
        HighScoreSortType.RIGHT_ANSWERS to HighScoreFilterItem(
            label = stringResource(R.string.high_scores_filter_score),
            selected = selected == HighScoreSortType.RIGHT_ANSWERS,
            icon = Icons.Filled.EmojiEvents,
        ),
        HighScoreSortType.QUESTIONS to HighScoreFilterItem(
            label = stringResource(R.string.high_scores_filter_questions),
            selected = selected == HighScoreSortType.QUESTIONS,
            icon = Icons.Filled.Numbers,
        ),
        HighScoreSortType.DATE to HighScoreFilterItem(
            label = stringResource(R.string.high_scores_filter_date),
            selected = selected == HighScoreSortType.DATE,
            icon = Icons.Filled.DateRange,
        ),
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = cardBorderStroke(),
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimens.tightSpacing),
            contentPadding = PaddingValues(horizontal = dimens.cardPadding),
            horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        ) {
            items(chips) { (sortType, item) ->
                HighScoreFilterChip(item = item, onClick = { onSelect(sortType) })
            }
        }
    }
}
