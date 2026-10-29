package goldrush.skeedromano.official.ui.composable.screen.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import goldrush.skeedromano.official.R
import goldrush.skeedromano.official.ui.composable.approot.topbar.AppTopBar
import goldrush.skeedromano.official.ui.composable.screen.about.card.AppInfoCard
import goldrush.skeedromano.official.ui.composable.screen.about.rules.header.RulesHeader
import goldrush.skeedromano.official.ui.composable.screen.about.rules.list.RulesList
import goldrush.skeedromano.official.ui.theme.LocalDimens
import goldrush.skeedromano.official.ui.theme.cardBorderStroke

@Composable
fun GSROLAboutScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = LocalDimens.current

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(R.string.about_title),
            onNavigateBack = onBackClick,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(dimens.sectionGap),
        ) {
            RulesCard(modifier = Modifier.fillMaxWidth())

            AppInfoCard(modifier = Modifier.fillMaxWidth())
        }
    }
}

/** Shell card is shared — only the header and the list inside it vary per axis. */
@Composable
private fun RulesCard(modifier: Modifier = Modifier) {
    val dimens = LocalDimens.current
    val rules = stringArrayResource(R.array.about_rules).toList()

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimens.cardCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        border = cardBorderStroke(),
    ) {
        Column(
            modifier = Modifier.padding(dimens.cardPadding),
            verticalArrangement = Arrangement.spacedBy(dimens.sectionGap),
        ) {
            RulesHeader(title = stringResource(R.string.about_rules_title))
            RulesList(rules = rules)
        }
    }
}
