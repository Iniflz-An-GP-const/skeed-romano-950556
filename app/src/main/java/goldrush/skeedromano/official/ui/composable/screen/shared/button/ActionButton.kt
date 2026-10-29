package goldrush.skeedromano.official.ui.composable.screen.shared.button

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButtonDefaults
import goldrush.skeedromano.official.ui.composable.screen.shared.button.ActionButtonItem
import goldrush.skeedromano.official.ui.composable.screen.shared.modifier.press.pressFeedback
import goldrush.skeedromano.official.ui.theme.controlBorderStroke

@Composable
fun ActionButton(
    item: ActionButtonItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = ActionButtonDefaults.shape
    val interactionSource = remember { MutableInteractionSource() }
    val border = controlBorderStroke()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(ActionButtonDefaults.Height)
            .alpha(if (item.enabled) 1f else ActionButtonDefaults.DisabledAlpha)
            .pressFeedback(interactionSource)
            .clip(shape)
            .background(color = ActionButtonDefaults.containerColor(item.emphasis), shape = shape)
            .let { base -> if (border != null) base.border(border, shape) else base }
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = item.enabled,
                onClick = onClick,
            )
            .padding(horizontal = ActionButtonDefaults.horizontalPadding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = item.text,
            color = ActionButtonDefaults.contentColor(item.emphasis),
            style = ActionButtonDefaults.labelStyle(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
