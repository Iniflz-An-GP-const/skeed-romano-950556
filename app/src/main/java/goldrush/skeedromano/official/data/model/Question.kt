package goldrush.skeedromano.official.data.model

data class Question(
    val id: Int,
    val texts: Map<String, String>,
    val rightAnswerId: Int,
    val answers: List<Answer>,
) {
    val text: String get() = texts.forCurrentLocale()
}