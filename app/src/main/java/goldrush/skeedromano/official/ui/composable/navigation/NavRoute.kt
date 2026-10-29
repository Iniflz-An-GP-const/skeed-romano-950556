package goldrush.skeedromano.official.ui.composable.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable
    object Splash : NavRoute()

    @Serializable
    object MainMenu : NavRoute()

    @Serializable
    object About : NavRoute()

    @Serializable
    data class TopicsSelection(val isTwoPlayerMode: Boolean) : NavRoute()

    @Serializable
    data class Game(
        val isTwoPlayerMode: Boolean,
        val topicIds: List<Int>,
    ) : NavRoute()

    @Serializable
    object HighScores : NavRoute()
}