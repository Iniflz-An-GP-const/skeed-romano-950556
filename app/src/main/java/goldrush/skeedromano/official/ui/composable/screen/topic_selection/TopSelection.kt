package goldrush.skeedromano.official.ui.composable.screen.topic_selection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.ui.composable.approot.topbar.AppTopBar
import goldrush.skeedromano.official.ui.composable.screen.shared.DataBasedContainer
import goldrush.skeedromano.official.ui.composable.screen.shared.DataEmptyContent
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButtonItem
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButton
import goldrush.skeedromano.official.ui.composable.screen.topic_selection.list.TopicsList
import goldrush.skeedromano.official.ui.state.DataUiState
import goldrush.skeedromano.official.ui.theme.LocalDimens
import goldrush.skeedromano.official.ui.viewmodel.TopicsSelectionViewModel

@Composable
fun TopicsSelectionScreen(
    modifier: Modifier = Modifier,
    viewModel: TopicsSelectionViewModel = koinViewModel(),
    isTwoPlayerMode: Boolean,
    onGameScreenNavigation: (Boolean, List<Int>) -> Unit,
    onBackClick: () -> Unit,
) {
    val topicsState by viewModel.topicsState.collectAsState()
    val selectedTopicIds by viewModel.selectedTopicIdsState.collectAsState()
    val dimens = LocalDimens.current

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(R.string.topics_selection_title),
            onNavigateBack = onBackClick,
            actions = {
                IconButton(onClick = viewModel::selectRandomTopics) {
                    Icon(
                        imageVector = Icons.Filled.Shuffle,
                        contentDescription = stringResource(R.string.shuffle_icon_description),
                    )
                }
            },
        )

        DataBasedContainer(
            modifier = Modifier.weight(1f),
            dataState = topicsState,
            dataPopulated = {
                val topics = (topicsState as DataUiState.Populated).data

                Column(modifier = Modifier.fillMaxSize()) {
                    TopicsList(
                        topics = topics,
                        selectedTopicIds = selectedTopicIds,
                        contentPadding = PaddingValues(
                            start = dimens.screenPadding,
                            end = dimens.screenPadding,
                            top = dimens.contentSpacing,
                            bottom = dimens.contentSpacing,
                        ),
                        onTopicClick = viewModel::selectTopic,
                        modifier = Modifier.weight(1f),
                    )

                    ActionButton(
                        item = ActionButtonItem(
                            text = stringResource(R.string.topics_selection_start),
                            icon = Icons.Filled.PlayArrow,
                            enabled = selectedTopicIds.isNotEmpty(),
                        ),
                        onClick = { onGameScreenNavigation(isTwoPlayerMode, selectedTopicIds) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimens.screenPadding),
                    )
                }
            },
            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.topics_selection_empty),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}
