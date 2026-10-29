package goldrush.skeedromano.official.ui.composable.screen.game_over.result

data class GameResultItem(
    val correctAnswers: Int,
    val totalQuestions: Int,
) {
    val accuracyPercent: Int
        get() = if (totalQuestions == 0) 0 else (correctAnswers * 100) / totalQuestions
}
