package goldrush.skeedromano.official.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import goldrush.skeedromano.official.ui.viewmodel.GameViewModel
import goldrush.skeedromano.official.ui.viewmodel.HighScoreViewModel
import goldrush.skeedromano.official.ui.viewmodel.TopicsSelectionViewModel

val viewModule = module {
    viewModel {
        TopicsSelectionViewModel(topicRepository = get())
    }

    viewModel {
        GameViewModel(
            savedStateHandle = get(),
            topicRepository = get(),
            highScoreRepository = get()
        )
    }

    viewModel {
        HighScoreViewModel(
            highScoreRepository = get()
        )
    }
}