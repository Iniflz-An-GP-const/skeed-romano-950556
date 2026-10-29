package goldrush.skeedromano.official.ui.composable.screen.high_scores.filter.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import goldrush.skeedromano.official.ui.composable.screen.high_scores.filter.HighScoreFilterItem
import goldrush.skeedromano.official.ui.theme.LocalDimens

private val UnderlineHeight = 2.dp
private val UnderlineWidth = 24.dp

/** No chip background at all — selection shown only by label weight/color and an underline bar. */
@Composable
fun HighScoreFilterChip(
    item: HighScoreFilterItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val contentColor = if (item.selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = dimens.tightSpacing, horizontal = dimens.itemSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = item.label,
            color = contentColor,
            fontWeight = if (item.selected) FontWeight.Bold else FontWeight.Normal,
            style = MaterialTheme.typography.labelLarge,
        )

        Box(
            modifier = Modifier
                .padding(top = dimens.tightSpacing)
                .height(UnderlineHeight)
                .width(UnderlineWidth)
                .background(if (item.selected) MaterialTheme.colorScheme.primary else Color.Transparent),
        )
    }
}
