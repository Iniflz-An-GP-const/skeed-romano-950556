package goldrush.skeedromano.official.data.model

data class Answer(
    val id: Int,
    val texts: Map<String, String>,
) {
    val text: String get() = texts.forCurrentLocale()
}