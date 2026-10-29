package goldrush.skeedromano.official.ui.composable.screen.main_menu.content.buttons.play_mode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButtonItem
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButton
import goldrush.skeedromano.official.ui.theme.LocalDimens

@Composable
fun PlayModeButtons(
    soloItem: ActionButtonItem,
    duoItem: ActionButtonItem,
    onSoloClick: () -> Unit,
    onDuoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        ActionButton(
            item = soloItem,
            onClick = onSoloClick,
            modifier = Modifier.weight(1f),
        )
        ActionButton(
            item = duoItem,
            onClick = onDuoClick,
            modifier = Modifier.weight(1f),
        )
    }
}
