package goldrush.skeedromano.official.data.model

data class GameFinishResult(
    val totalQuestions: Int,
    val player1Correct: Int,
    val player2Correct: Int,
    val isTwoPlayerMode: Boolean,
    val player1BestStreak: Int,
    val player2BestStreak: Int,
)