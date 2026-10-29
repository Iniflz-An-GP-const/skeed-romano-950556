package goldrush.skeedromano.official.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import goldrush.skeedromano.official.data.model.Topic
import goldrush.skeedromano.official.data.repository.TopicRepository
import goldrush.skeedromano.official.ui.state.DataUiState

class TopicsSelectionViewModel(
    private val topicRepository: TopicRepository,
) : ViewModel() {
    private val _topicsState = MutableStateFlow<DataUiState<List<Topic>>>(DataUiState.Initial)
    val topicsState: StateFlow<DataUiState<List<Topic>>> = _topicsState.asStateFlow()

    private val _selectedTopicIdsState = MutableStateFlow<List<Int>>(emptyList())
    val selectedTopicIdsState: StateFlow<List<Int>> = _selectedTopicIdsState.asStateFlow()

    init {
        loadTopics()
    }

    private fun loadTopics() {
        _topicsState.value = DataUiState.from(topicRepository.getTopics())
    }

    fun selectTopic(topicId: Int) {
        _selectedTopicIdsState.value = if (topicId in _selectedTopicIdsState.value) {
            _selectedTopicIdsState.value - topicId
        } else {
            _selectedTopicIdsState.value + topicId
        }
    }

    fun selectRandomTopics() {
        val topics = (_topicsState.value as? DataUiState.Populated)?.data ?: return
        if (topics.size <= 1) return

        val currentSelection = _selectedTopicIdsState.value.toSet()

        var newSelection: List<Int>

        do {
            val maxCount = minOf(5, topics.size - 1)
            val count = (1..maxCount).random()

            newSelection = topics
                .shuffled()
                .take(count)
                .map(Topic::id)
        } while (newSelection.toSet() == currentSelection)

        _selectedTopicIdsState.value = newSelection
    }
}
