package goldrush.skeedromano.official.ui.composable.screen.topic_selection.card

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import goldrush.skeedromano.official.ui.theme.LocalDimens

/** Shared look of every topic-card variant — shape and spacing come from [LocalDimens]. */
internal object TopicCardDefaults {
    /**
     * Title is always reserved exactly this many lines (short titles get blank space
     * below them) so cards next to each other — in a list or a grid — end up the same
     * height regardless of how long each one's title is.
     */
    const val TitleLines: Int = 2

    val shape: Shape
        @Composable get() = RoundedCornerShape(LocalDimens.current.cardCornerRadius)

    val padding: Dp
        @Composable get() = LocalDimens.current.cardPadding

    val titleSpacing: Dp
        @Composable get() = LocalDimens.current.tightSpacing

    @Composable
    fun titleStyle(): TextStyle = MaterialTheme.typography.titleMedium

    @Composable
    fun countStyle(): TextStyle = MaterialTheme.typography.bodySmall
}
