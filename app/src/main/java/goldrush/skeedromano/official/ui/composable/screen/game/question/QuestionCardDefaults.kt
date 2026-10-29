package goldrush.skeedromano.official.ui.composable.screen.game.question

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import goldrush.skeedromano.official.ui.theme.LocalDimens

/** Shared look of every question-card variant — shape and padding come from [LocalDimens]. */
internal object QuestionCardDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(LocalDimens.current.cardCornerRadius)

    val padding: Dp
        @Composable get() = LocalDimens.current.cardPadding

    @Composable
    fun textStyle(): TextStyle = MaterialTheme.typography.headlineSmall
}
