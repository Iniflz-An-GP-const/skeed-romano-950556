package goldrush.skeedromano.official.ui.composable.screen.game.answer

enum class AnswerState {
    Neutral,
    Correct,
    Wrong,
}

data class AnswerButtonItem(
    val text: String,
    val state: AnswerState = AnswerState.Neutral,
    val letter: Char? = null,
)
