package goldrush.skeedromano.official.ui.composable.screen.topic_selection.card

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.ui.composable.screen.shared.modifier.press.pressFeedback
import goldrush.skeedromano.official.ui.composable.screen.topic_selection.card.TopicCardDefaults
import goldrush.skeedromano.official.ui.composable.screen.topic_selection.card.TopicCardItem

private val SelectedBorderWidth = 2.dp

/** Selected state shown by a colored border and a tinted container, nothing else moves. */
@Composable
fun TopicCard(
    item: TopicCardItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = TopicCardDefaults.shape
    val interactionSource = remember { MutableInteractionSource() }

    val containerColor = if (item.isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (item.isSelected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val borderColor = if (item.isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .pressFeedback(interactionSource)
            .clip(shape)
            .background(color = containerColor, shape = shape)
            .border(width = SelectedBorderWidth, color = borderColor, shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick,
            )
            .padding(TopicCardDefaults.padding),
        verticalArrangement = Arrangement.spacedBy(TopicCardDefaults.titleSpacing),
    ) {
        Text(
            text = item.title,
            color = contentColor,
            style = TopicCardDefaults.titleStyle(),
            minLines = TopicCardDefaults.TitleLines,
            maxLines = TopicCardDefaults.TitleLines,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = pluralStringResource(R.plurals.topic_question_count, item.questionCount, item.questionCount),
            color = contentColor,
            style = TopicCardDefaults.countStyle(),
        )
    }
}
