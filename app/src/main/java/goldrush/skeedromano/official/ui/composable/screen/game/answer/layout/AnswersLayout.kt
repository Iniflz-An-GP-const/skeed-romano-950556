package goldrush.skeedromano.official.ui.composable.screen.game.answer.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import goldrush.skeedromano.official.ui.composable.screen.game.answer.AnswerButtonItem
import goldrush.skeedromano.official.ui.composable.screen.game.answer.button.AnswerButton
import goldrush.skeedromano.official.ui.theme.LocalDimens

/**
 * Two answers per row; a leftover last answer (odd count) spans the full width on its own row.
 * Buttons keep their fixed size — the group is centered in whatever space [modifier] gives it,
 * rather than stretching rows to fill it.
 */
@Composable
fun AnswersLayout(
    items: List<AnswerButtonItem>,
    onAnswerClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing, Alignment.CenterVertically),
    ) {
        items.chunked(2).forEachIndexed { rowIndex, row ->
            if (row.size == 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                ) {
                    row.forEachIndexed { columnIndex, item ->
                        val index = rowIndex * 2 + columnIndex
                        AnswerButton(
                            item = item,
                            onClick = { onAnswerClick(index) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            } else {
                val index = rowIndex * 2
                AnswerButton(
                    item = row[0],
                    onClick = { onAnswerClick(index) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
