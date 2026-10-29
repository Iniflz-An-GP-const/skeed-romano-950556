package goldrush.skeedromano.official.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import goldrush.skeedromano.official.data.entity.HighScore
import goldrush.skeedromano.official.data.model.GameFinishResult
import goldrush.skeedromano.official.data.model.Question
import goldrush.skeedromano.official.data.repository.HighScoreRepository
import goldrush.skeedromano.official.data.repository.TopicRepository
import goldrush.skeedromano.official.ui.composable.navigation.NavRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.milliseconds

class GameViewModel(
    savedStateHandle: SavedStateHandle,
    private val topicRepository: TopicRepository,
    private val highScoreRepository: HighScoreRepository,
) : ViewModel() {
    private val args = savedStateHandle.toRoute<NavRoute.Game>()

    val topicIds = args.topicIds
    val isTwoPlayerMode = args.isTwoPlayerMode

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    val totalQuestions: Int
        get() = _questions.value.size

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion.asStateFlow()

    private val _remainingTimeMs = MutableStateFlow(TOTAL_DURATION_MS)
    val remainingTimeMs: StateFlow<Long> = _remainingTimeMs.asStateFlow()

    private val _selectedAnswerId = MutableStateFlow<Int?>(null)
    val selectedAnswerId: StateFlow<Int?> = _selectedAnswerId.asStateFlow()

    private val _isAnswered = MutableStateFlow(false)
    val isAnswered: StateFlow<Boolean> = _isAnswered.asStateFlow()

    private val _correctAnswersCount = MutableStateFlow(0)

    // Each player keeps their own streak — in solo mode currentPlayer never leaves 1, so
    // this reduces to a single streak; in two-player mode it's always the turn-taker's own.
    private val _player1Streak = MutableStateFlow(0)
    private val _player2Streak = MutableStateFlow(0)
    private var player1BestStreak = 0
    private var player2BestStreak = 0

    // Two-player mode state
    private val _currentPlayer = MutableStateFlow(1)
    val currentPlayer: StateFlow<Int> = _currentPlayer.asStateFlow()

    val currentStreak: StateFlow<Int> = combine(
        _currentPlayer,
        _player1Streak,
        _player2Streak,
    ) { player, player1Streak, player2Streak ->
        if (player == 1) player1Streak else player2Streak
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    private val _player1Correct = MutableStateFlow(0)
    private val _player2Correct = MutableStateFlow(0)

    private val _isGameFinished = MutableStateFlow<GameFinishResult?>(null)
    val isGameFinished: StateFlow<GameFinishResult?> = _isGameFinished.asStateFlow()

    private var timerJob: Job? = null
    private var transitionJob: Job? = null

    init {
        loadQuestionsAndStart()
    }

    private fun loadQuestionsAndStart() {
        val allQuestions = topicRepository.getTopics()
            .filter { it.id in topicIds }
            .flatMap { it.questions }
            .shuffled()
        _questions.value = allQuestions
        _currentQuestion.value = allQuestions.firstOrNull()

        viewModelScope.launch {
            delay(2000.milliseconds)
            _isLoading.value = false
            startTimer()
        }
    }

    /** Resets all round/score state and reshuffles the same topics for a fresh run. */
    fun restartGame() {
        timerJob?.cancel()
        transitionJob?.cancel()

        _isLoading.value = true
        _currentQuestionIndex.value = 0
        _selectedAnswerId.value = null
        _isAnswered.value = false
        _correctAnswersCount.value = 0
        _player1Streak.value = 0
        _player2Streak.value = 0
        player1BestStreak = 0
        player2BestStreak = 0
        _currentPlayer.value = 1
        _player1Correct.value = 0
        _player2Correct.value = 0
        _isGameFinished.value = null

        loadQuestionsAndStart()
    }

    private fun startTimer() {
        timerJob?.cancel()
        _remainingTimeMs.value = TOTAL_DURATION_MS
        timerJob = viewModelScope.launch {
            val tickIntervalMs = 100L
            var elapsedMs = 0L

            while (elapsedMs < TOTAL_DURATION_MS && !_isAnswered.value) {
                delay(tickIntervalMs.milliseconds)
                elapsedMs += tickIntervalMs
                _remainingTimeMs.value = (TOTAL_DURATION_MS - elapsedMs).coerceAtLeast(0L)
            }

            if (!_isAnswered.value) {
                _isAnswered.value = true
                resetCurrentPlayerStreak()
                transitionJob?.cancel()
                transitionJob = viewModelScope.launch {
                    delay(NEXT_ROUND_DELAY.milliseconds)
                    nextQuestion()
                }
            }
        }
    }

    fun selectAnswer(answerId: Int) {
        val current = _currentQuestion.value ?: return
        if (_isAnswered.value) return

        _selectedAnswerId.value = answerId
        _isAnswered.value = true
        timerJob?.cancel()

        if (answerId == current.rightAnswerId) {
            _correctAnswersCount.value += 1

            if (_currentPlayer.value == 1) {
                _player1Streak.value += 1
                player1BestStreak = maxOf(player1BestStreak, _player1Streak.value)
                if (isTwoPlayerMode) _player1Correct.value += 1
            } else {
                _player2Streak.value += 1
                player2BestStreak = maxOf(player2BestStreak, _player2Streak.value)
                _player2Correct.value += 1
            }
        } else {
            resetCurrentPlayerStreak()
        }

        transitionJob?.cancel()
        transitionJob = viewModelScope.launch {
            delay(NEXT_ROUND_DELAY.milliseconds)
            nextQuestion()
        }
    }

    private fun resetCurrentPlayerStreak() {
        if (_currentPlayer.value == 1) {
            _player1Streak.value = 0
        } else {
            _player2Streak.value = 0
        }
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _selectedAnswerId.value = null
            _isAnswered.value = false

            if (isTwoPlayerMode) {
                _currentPlayer.value = if (_currentPlayer.value == 1) 2 else 1
            }

            _currentQuestionIndex.value += 1
            _currentQuestion.value = _questions.value[_currentQuestionIndex.value]
            startTimer()
        } else {
            onGameFinished()
        }
    }

    private fun onGameFinished() {
        val totalQuestions = _questions.value.size

        _isGameFinished.value = GameFinishResult(
            totalQuestions = totalQuestions,
            player1Correct = if (isTwoPlayerMode) _player1Correct.value else _correctAnswersCount.value,
            player2Correct = _player2Correct.value,
            isTwoPlayerMode = isTwoPlayerMode,
            player1BestStreak = player1BestStreak,
            player2BestStreak = player2BestStreak,
        )

        if (!isTwoPlayerMode) {
            saveHighScore(
                questionsCount = totalQuestions,
                correctAnswers = _correctAnswersCount.value
            )
        }
    }

    private fun saveHighScore(questionsCount: Int, correctAnswers: Int) {
        viewModelScope.launch {
            val highScore = HighScore(
                questions = questionsCount,
                rightAnswers = correctAnswers,
                timestamp = LocalDateTime.now(),
            )
            highScoreRepository.saveIfBetter(highScore)
        }
    }

    companion object {
        const val TOTAL_DURATION_MS = 15000L
        private const val NEXT_ROUND_DELAY = 2000L
    }
}