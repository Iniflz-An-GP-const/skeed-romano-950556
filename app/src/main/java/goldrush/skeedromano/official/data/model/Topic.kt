package goldrush.skeedromano.official.data.model

data class Topic(
    val id: Int,
    val texts: Map<String, String>,
    val questions: List<Question>,
) {
    val name: String get() = texts.forCurrentLocale()
}