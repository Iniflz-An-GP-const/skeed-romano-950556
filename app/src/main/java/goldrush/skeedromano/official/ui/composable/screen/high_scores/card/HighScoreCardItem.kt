package goldrush.skeedromano.official.ui.composable.screen.high_scores.card

data class HighScoreCardItem(
    val correctAnswers: Int,
    val totalQuestions: Int,
    val formattedDate: String,
) {
    val accuracyPercent: Int
        get() = if (totalQuestions == 0) 0 else (correctAnswers * 100) / totalQuestions
}
