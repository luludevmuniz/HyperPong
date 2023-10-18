package com.alpaca.hyperpong.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alpaca.hyperpong.navigation.ARG_EVENT_ID
import com.alpaca.hyperpong.navigation.HomeScreen
import com.alpaca.hyperpong.presentation.screens.about.event.AboutEventScreen
import com.alpaca.hyperpong.presentation.screens.about.aulas.AboutAulasScreen
import com.alpaca.hyperpong.presentation.screens.about.events.AboutEventsScreen
import com.alpaca.hyperpong.presentation.screens.home.HomeContent

@Composable
fun HomeNavGraph(navController: NavHostController, onNavigationIconClicked: () -> Unit) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeScreen.Home.route
    ) {
        composable(route = HomeScreen.Home.route) {
            HomeContent(
                onNavigationIconClicked = { onNavigationIconClicked() },
                onEventClicked = {  idEvento ->
                    navController.navigate(route = HomeScreen.EventDetails.passId(idEvento)) {
                        popUpTo(route = HomeScreen.Home.route)
                        launchSingleTop
                    }
                },
                onAulaClicked = {}
            )
        }
        composable(route = HomeScreen.Settings.route) {

        }
        composable(route = HomeScreen.AboutUs.route) {

        }
        composable(route = HomeScreen.AboutLessons.route) {
            AboutAulasScreen(onNavigationIconClicked = { onNavigationIconClicked() })
        }
        composable(route = HomeScreen.AboutEvents.route) {
            AboutEventsScreen(onNavigationIconClicked = { onNavigationIconClicked() })
        }
        composable(
            route = HomeScreen.EventDetails.route,
            arguments = listOf(navArgument(ARG_EVENT_ID) { type = NavType.StringType })
        ) {backStackEntry ->
            AboutEventScreen(
                eventoId = backStackEntry.arguments?.getString(ARG_EVENT_ID).orEmpty(),
                onNavigationIconClicked = { onNavigationIconClicked() }
            )
        }
    }
}