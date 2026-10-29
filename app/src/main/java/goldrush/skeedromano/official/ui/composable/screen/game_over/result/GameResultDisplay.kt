package goldrush.skeedromano.official.ui.composable.screen.game_over.result

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.ui.composable.screen.game_over.result.GameResultItem

/** Just the score, as a big bold fraction — the plainest possible read. */
@Composable
fun GameResultDisplay(
    item: GameResultItem,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(
            R.string.high_score_card_score_format,
            item.correctAnswers,
            item.totalQuestions,
        ),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier,
    )
}
