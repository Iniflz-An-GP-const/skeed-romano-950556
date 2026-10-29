package goldrush.skeedromano.official.ui.composable.screen.about.rules.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import goldrush.skeedromano.official.ui.theme.ControlDivider
import goldrush.skeedromano.official.ui.theme.LocalDimens

@Composable
fun RulesHeader(title: String, modifier: Modifier = Modifier) {
    val dimens = LocalDimens.current

    Column(modifier = modifier) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(dimens.contentSpacing))

        ControlDivider()
    }
}
