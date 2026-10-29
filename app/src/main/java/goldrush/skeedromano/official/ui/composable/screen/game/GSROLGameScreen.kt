package goldrush.skeedromano.official.ui.composable.screen.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.data.model.Question
import goldrush.skeedromano.official.ui.composable.screen.game.answer.AnswerButtonItem
import goldrush.skeedromano.official.ui.composable.screen.game.answer.AnswerState
import goldrush.skeedromano.official.ui.composable.screen.game.answer.layout.AnswersLayout
import goldrush.skeedromano.official.ui.composable.screen.game.question.QuestionCardItem
import goldrush.skeedromano.official.ui.composable.screen.game.question.QuestionCard
import goldrush.skeedromano.official.ui.composable.screen.game.streak.StreakItem
import goldrush.skeedromano.official.ui.composable.screen.game.streak.StreakIndicator
import goldrush.skeedromano.official.ui.composable.screen.game.timer.TimerIndicatorItem
import goldrush.skeedromano.official.ui.composable.screen.game.transition.QuestionTransition
import goldrush.skeedromano.official.ui.composable.screen.game.timer.TimerIndicator
import goldrush.skeedromano.official.ui.composable.screen.game_over.GameOverDialog
import goldrush.skeedromano.official.ui.composable.screen.shared.ConfirmationDialog
import goldrush.skeedromano.official.ui.theme.LocalDimens
import goldrush.skeedromano.official.ui.viewmodel.GameViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.ceil

private const val StreakVisibilityThreshold = 2

@Composable
fun GSROLGameScreen(
    onMainMenu: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = koinViewModel(),
) {
    val dimens = LocalDimens.current

    val currentQuestion by gameViewModel.currentQuestion.collectAsState()
    val currentQuestionIndex by gameViewModel.currentQuestionIndex.collectAsState()
    val remainingTimeMs by gameViewModel.remainingTimeMs.collectAsState()
    val selectedAnswerId by gameViewModel.selectedAnswerId.collectAsState()
    val isAnswered by gameViewModel.isAnswered.collectAsState()
    val isGameFinished by gameViewModel.isGameFinished.collectAsState()
    val currentStreak by gameViewModel.currentStreak.collectAsState()

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = isGameFinished == null) {
        showExitDialog = true
    }

    if (showExitDialog) {
        ConfirmationDialog(
            title = stringResource(R.string.dialog_exit_title),
            message = stringResource(R.string.dialog_exit_message),
            onConfirm = {
                showExitDialog = false
                onBackClick()
            },
            onDismiss = { showExitDialog = false },
        )
    }

    isGameFinished?.let { result ->
        GameOverDialog(
            totalQuestions = result.totalQuestions,
            player1CorrectAnswers = result.player1Correct,
            player2CorrectAnswers = result.player2Correct,
            isTwoPlayerMode = result.isTwoPlayerMode,
            player1BestStreak = result.player1BestStreak,
            player2BestStreak = result.player2BestStreak,
            onPlayAgain = gameViewModel::restartGame,
            onMainMenu = onMainMenu,
        )
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.tightSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = { showExitDialog = true }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_icon_description),
                )
            }

            if (currentStreak >= StreakVisibilityThreshold) {
                StreakIndicator(
                    item = StreakItem(count = currentStreak),
                    modifier = Modifier.padding(end = dimens.itemSpacing),
                )
            }
        }

        val roundState = currentQuestion?.let { question ->
            RoundState(
                questionIndex = currentQuestionIndex,
                question = question,
                selectedAnswerId = selectedAnswerId,
                isAnswered = isAnswered,
                remainingTimeMs = remainingTimeMs,
            )
        }

        // Last known state of each round, so the outgoing question keeps its highlighted
        // answers and timer while animating out (the ViewModel resets them immediately)
        val roundSnapshots = remember { HashMap<Int, RoundState>() }
        SideEffect {
            roundState?.let { roundSnapshots[it.questionIndex] = it }
        }

        if (roundState == null) return@Column

        // Target is only the index: it changes once per question, so the transition isn't
        // re-targeted by every timer tick
        QuestionTransition(
            questionIndex = currentQuestionIndex,
            modifier = Modifier.fillMaxSize(),
        ) { questionIndex ->
            val round = if (questionIndex == roundState.questionIndex) {
                roundState
            } else {
                roundSnapshots[questionIndex]
            } ?: return@QuestionTransition
            val question = round.question

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimens.screenPadding, vertical = dimens.contentSpacing),
                verticalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
            ) {
                QuestionCard(
                    item = QuestionCardItem(
                        text = question.text,
                        questionNumber = round.questionIndex + 1,
                        totalQuestions = gameViewModel.totalQuestions,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )

                TimerIndicator(
                    item = TimerIndicatorItem(
                        progress = round.remainingTimeMs.toFloat() / GameViewModel.TOTAL_DURATION_MS,
                        remainingSeconds = ceil(round.remainingTimeMs / 1000f).toInt(),
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )

                val answerItems = question.answers.mapIndexed { index, answer ->
                    val state = when {
                        round.isAnswered && answer.id == question.rightAnswerId -> AnswerState.Correct
                        round.isAnswered && answer.id == round.selectedAnswerId -> AnswerState.Wrong
                        else -> AnswerState.Neutral
                    }

                    AnswerButtonItem(
                        text = answer.text,
                        state = state,
                        letter = 'A' + index,
                    )
                }

                AnswersLayout(
                    items = answerItems,
                    onAnswerClick = { index ->
                        // Ignore taps on the outgoing question while it animates away
                        if (round.questionIndex == currentQuestionIndex) {
                            gameViewModel.selectAnswer(question.answers[index].id)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )
            }
        }
    }
}

private data class RoundState(
    val questionIndex: Int,
    val question: Question,
    val selectedAnswerId: Int?,
    val isAnswered: Boolean,
    val remainingTimeMs: Long,
)
