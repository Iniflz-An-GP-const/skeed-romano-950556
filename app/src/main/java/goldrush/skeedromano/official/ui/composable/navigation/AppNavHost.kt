package goldrush.skeedromano.official.ui.composable.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import goldrush.skeedromano.official.ui.composable.screen.about.GSROLAboutScreen
import goldrush.skeedromano.official.ui.composable.screen.game.GSROLGameScreen
import goldrush.skeedromano.official.ui.composable.screen.high_scores.GSROLHighScoresScreen
import goldrush.skeedromano.official.ui.composable.screen.main_menu.GSROLMainMenuScreen
import goldrush.skeedromano.official.ui.composable.screen.splash.GSROLSplashScreen
import goldrush.skeedromano.official.ui.composable.screen.topic_selection.TopicsSelectionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current

    NavHost(
        navController = navController,
        startDestination = NavRoute.Splash,
        modifier = modifier,
    ) {
        composable<NavRoute.Splash> {
            GSROLSplashScreen(
                onMainMenuScreenNavigation = {
                    navController.navigate(route = NavRoute.MainMenu) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.MainMenu> {
            GSROLMainMenuScreen(
                onTopicsSelectionScreenNavigation = { isTwoPlayerMode: Boolean ->
                    navController.navigate(
                        route = NavRoute.TopicsSelection(isTwoPlayerMode = isTwoPlayerMode)
                    )
                },
                onAboutScreenNavigation = { navController.navigate(route = NavRoute.About) },
                onHighScoresScreenNavigation = { navController.navigate(route = NavRoute.HighScores) },
                onExit = { activity?.finishAffinity() }
            )
        }

        composable<NavRoute.TopicsSelection> { backStackEntry ->
            val topicsSelectionRoute: NavRoute.TopicsSelection = backStackEntry.toRoute()
            TopicsSelectionScreen(
                isTwoPlayerMode = topicsSelectionRoute.isTwoPlayerMode,
                onGameScreenNavigation = { isTwoPlayerMode: Boolean, topicIds: List<Int> ->
                    navController.navigate(
                        route = NavRoute.Game(
                            isTwoPlayerMode = isTwoPlayerMode,
                            topicIds = topicIds,
                        )
                    )
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<NavRoute.Game> {
            GSROLGameScreen(
                onMainMenu = {
                    navController.navigate(route = NavRoute.MainMenu) {
                        popUpTo(NavRoute.MainMenu) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<NavRoute.About> {
            GSROLAboutScreen(onBackClick = { navController.popBackStack() })
        }

        composable<NavRoute.HighScores> {
            GSROLHighScoresScreen(onBackClick = { navController.popBackStack() })
        }
    }
}