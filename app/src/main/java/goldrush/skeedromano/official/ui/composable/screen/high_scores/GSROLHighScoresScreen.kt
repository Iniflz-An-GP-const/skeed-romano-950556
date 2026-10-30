package goldrush.skeedromano.official.ui.composable.screen.high_scores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.data.entity.HighScore
import goldrush.skeedromano.official.ui.composable.approot.topbar.AppTopBar
import goldrush.skeedromano.official.ui.composable.screen.high_scores.card.HighScoreCardItem
import goldrush.skeedromano.official.ui.composable.screen.high_scores.card.HighScoreCard
import goldrush.skeedromano.official.ui.composable.screen.high_scores.filter.HighScoreFilter
import goldrush.skeedromano.official.ui.composable.screen.shared.DataBasedContainer
import goldrush.skeedromano.official.ui.composable.screen.shared.DataEmptyContent
import goldrush.skeedromano.official.ui.state.DataUiState
import goldrush.skeedromano.official.ui.theme.LocalDimens
import goldrush.skeedromano.official.ui.viewmodel.HighScoreViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

private val DateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US)

@Composable
fun GSROLHighScoresScreen(
    modifier: Modifier = Modifier,
    viewModel: HighScoreViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val highScoresState by viewModel.highScoresState.collectAsState()
    val selectedSortType by viewModel.selectedSortTypeState.collectAsState()
    val dimens = LocalDimens.current

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(R.string.high_scores_title),
            onNavigateBack = onBackClick,
        )

        DataBasedContainer(
            modifier = Modifier.weight(1f),
            dataState = highScoresState,
            dataPopulated = {
                val highScores = (highScoresState as DataUiState.Populated).data

                Column(modifier = Modifier.fillMaxSize()) {
                    HighScoreFilter(
                        selected = selectedSortType,
                        onSelect = viewModel::onSortTypeSelected,
                        modifier = Modifier.padding(
                            horizontal = dimens.screenPadding,
                            vertical = dimens.contentSpacing,
                        ),
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            start = dimens.screenPadding,
                            end = dimens.screenPadding,
                            bottom = dimens.contentSpacing,
                        ),
                        verticalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
                    ) {
                        items(highScores, key = HighScore::id) { highScore ->
                            HighScoreCard(
                                item = HighScoreCardItem(
                                    correctAnswers = highScore.rightAnswers,
                                    totalQuestions = highScore.questions,
                                    formattedDate = highScore.timestamp.format(DateFormatter),
                                ),
                            )
                        }
                    }
                }
            },
            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.high_scores_empty),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}
