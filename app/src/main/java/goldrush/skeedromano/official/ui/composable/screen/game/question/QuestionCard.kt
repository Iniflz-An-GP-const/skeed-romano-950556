package goldrush.skeedromano.official.ui.composable.screen.game.question

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import goldrush.skeedromano.official.ui.composable.screen.game.question.QuestionCardDefaults
import goldrush.skeedromano.official.ui.composable.screen.game.question.QuestionCardItem
import goldrush.skeedromano.official.ui.theme.cardBorderStroke

/** Just the question text, centered in a plain card — nothing else to it. */
@Composable
fun QuestionCard(
    item: QuestionCardItem,
    modifier: Modifier = Modifier,
) {
    val shape = QuestionCardDefaults.shape
    val border = cardBorderStroke()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = shape)
            .let { base -> if (border != null) base.border(border, shape) else base }
            .padding(QuestionCardDefaults.padding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = item.text,
            style = QuestionCardDefaults.textStyle(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}
