package goldrush.skeedromano.official.ui.composable.screen.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import goldrush.skeedromano.official.ui.state.DataUiState

@Composable
fun <T> DataBasedContainer(
    modifier: Modifier = Modifier,
    dataState: DataUiState<T>,
    dataPopulated: @Composable (() -> Unit),
    dataEmpty: @Composable (() -> Unit),
    dataInitial: @Composable (() -> Unit) = {},
) {
    Box(modifier = modifier) {
        when (dataState) {
            DataUiState.Initial -> {
                dataInitial()
            }

            is DataUiState.Populated -> {
                dataPopulated()
            }

            DataUiState.Empty -> {
                dataEmpty()
            }
        }
    }
}
