package goldrush.skeedromano.official.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import goldrush.skeedromano.official.data.entity.HighScore
import goldrush.skeedromano.official.data.model.HighScoreSortType
import goldrush.skeedromano.official.data.repository.HighScoreRepository
import goldrush.skeedromano.official.ui.state.DataUiState

class HighScoreViewModel(
    private val highScoreRepository: HighScoreRepository
) : ViewModel() {

    private val _highScoresState = MutableStateFlow<DataUiState<List<HighScore>>>(DataUiState.Initial)
    private val _selectedSortTypeState = MutableStateFlow(HighScoreSortType.RIGHT_ANSWERS)

    val selectedSortTypeState: StateFlow<HighScoreSortType> = _selectedSortTypeState.asStateFlow()

    val highScoresState: StateFlow<DataUiState<List<HighScore>>> =
        combine(_highScoresState, _selectedSortTypeState) { state, sortType ->
            when (state) {
                is DataUiState.Populated -> DataUiState.Populated(sortScores(state.data, sortType))
                else -> state
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataUiState.Initial
        )

    init {
        loadHighScores()
    }

    fun loadHighScores() {
        viewModelScope.launch {
            _highScoresState.value = DataUiState.from(highScoreRepository.getAll())
        }
    }

    fun onSortTypeSelected(sortType: HighScoreSortType) {
        _selectedSortTypeState.value = sortType
    }

    fun deleteHighScores() {
        viewModelScope.launch {
            highScoreRepository.deleteAll()
            _highScoresState.value = DataUiState.Empty
        }
    }

    private fun sortScores(scores: List<HighScore>, sortType: HighScoreSortType): List<HighScore> =
        when (sortType) {
            HighScoreSortType.RIGHT_ANSWERS -> scores.sortedByDescending { it.rightAnswers }
            HighScoreSortType.QUESTIONS -> scores.sortedByDescending { it.questions }
            HighScoreSortType.DATE -> scores.sortedByDescending { it.timestamp }
        }
}
