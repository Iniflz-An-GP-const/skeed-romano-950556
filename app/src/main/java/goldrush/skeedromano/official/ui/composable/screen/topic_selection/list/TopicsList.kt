package goldrush.skeedromano.official.ui.composable.screen.topic_selection.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import goldrush.skeedromano.official.data.model.Topic
import goldrush.skeedromano.official.ui.composable.screen.topic_selection.card.TopicCardItem
import goldrush.skeedromano.official.ui.composable.screen.topic_selection.card.TopicCard
import goldrush.skeedromano.official.ui.theme.LocalDimens

private const val ColumnCount = 2

/** Two topics per row — cards keep a stable height (see TopicCardDefaults.TitleLines), so rows stay even. */
@Composable
fun TopicsList(
    topics: List<Topic>,
    selectedTopicIds: List<Int>,
    contentPadding: PaddingValues,
    onTopicClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(ColumnCount),
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
        horizontalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
    ) {
        items(topics, key = Topic::id) { topic ->
            TopicCard(
                item = TopicCardItem(
                    title = topic.name,
                    questionCount = topic.questions.size,
                    isSelected = topic.id in selectedTopicIds,
                ),
                onClick = { onTopicClick(topic.id) },
            )
        }
    }
}
