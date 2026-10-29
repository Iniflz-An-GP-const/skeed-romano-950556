package goldrush.skeedromano.official.ui.composable.screen.game_over

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.ui.composable.screen.game_over.player.PlayerScoreItem
import goldrush.skeedromano.official.ui.composable.screen.game_over.player.PlayerScoreCard
import goldrush.skeedromano.official.ui.composable.screen.game_over.result.GameResultItem
import goldrush.skeedromano.official.ui.composable.screen.game_over.result.GameResultDisplay
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButtonEmphasis
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButtonItem
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButton
import goldrush.skeedromano.official.ui.theme.LocalDimens

private const val BestStreakVisibilityThreshold = 2

/**
 * Shown as a modal overlay on top of the Game screen when a run finishes, instead of
 * navigating to a separate screen — the game underneath stays mounted so "Play Again" can
 * just reset the existing ViewModel in place. Not dismissible by back/outside tap: the
 * player has to pick Play Again or Main Menu.
 */
@Composable
fun GameOverDialog(
    totalQuestions: Int,
    player1CorrectAnswers: Int,
    player2CorrectAnswers: Int,
    isTwoPlayerMode: Boolean,
    player1BestStreak: Int,
    player2BestStreak: Int,
    onPlayAgain: () -> Unit,
    onMainMenu: () -> Unit,
) {
    val dimens = LocalDimens.current

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.screenPadding),
            shape = RoundedCornerShape(dimens.cardCornerRadius),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.cardPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
            ) {
                Text(
                    text = stringResource(R.string.game_over_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                if (isTwoPlayerMode) {
                    val winner = when {
                        player1CorrectAnswers > player2CorrectAnswers -> 1
                        player2CorrectAnswers > player1CorrectAnswers -> 2
                        else -> null
                    }

                    Text(
                        text = winner?.let { stringResource(R.string.game_over_winner_format, it) }
                            ?: stringResource(R.string.game_over_tie),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
                    ) {
                        PlayerScoreCard(
                            item = PlayerScoreItem(
                                playerNumber = 1,
                                correctAnswers = player1CorrectAnswers,
                                totalQuestions = totalQuestions,
                                isWinner = winner == 1,
                            ),
                            modifier = Modifier.weight(1f),
                        )
                        PlayerScoreCard(
                            item = PlayerScoreItem(
                                playerNumber = 2,
                                correctAnswers = player2CorrectAnswers,
                                totalQuestions = totalQuestions,
                                isWinner = winner == 2,
                            ),
                            modifier = Modifier.weight(1f),
                        )
                    }
                } else {
                    GameResultDisplay(
                        item = GameResultItem(
                            correctAnswers = player1CorrectAnswers,
                            totalQuestions = totalQuestions,
                        ),
                    )
                }

                if (isTwoPlayerMode) {
                    if (player1BestStreak >= BestStreakVisibilityThreshold) {
                        Text(
                            text = stringResource(
                                R.string.game_over_player_best_streak_format,
                                1,
                                player1BestStreak,
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    if (player2BestStreak >= BestStreakVisibilityThreshold) {
                        Text(
                            text = stringResource(
                                R.string.game_over_player_best_streak_format,
                                2,
                                player2BestStreak,
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else if (player1BestStreak >= BestStreakVisibilityThreshold) {
                    Text(
                        text = stringResource(R.string.game_over_best_streak_format, player1BestStreak),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                ActionButton(
                    item = ActionButtonItem(
                        text = stringResource(R.string.game_over_play_again),
                        icon = Icons.Filled.Replay,
                    ),
                    onClick = onPlayAgain,
                    modifier = Modifier.fillMaxWidth(),
                )

                ActionButton(
                    item = ActionButtonItem(
                        text = stringResource(R.string.game_over_main_menu),
                        icon = Icons.Filled.Home,
                        emphasis = ActionButtonEmphasis.Alternate,
                    ),
                    onClick = onMainMenu,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
