package com.alpaca.hyperpong.navigation

sealed class AuthScreen(val route: String) {
    data object Login: AuthScreen("login_screen")
    data object Register: AuthScreen("register_screen")
}

sealed class HomeScreen(val route: String) {
    data object Home: HomeScreen("home_screen")
    data object Settings: HomeScreen("settings_screen")
    data object AboutUs: HomeScreen("about_us_screen")
    data object AboutLessons: HomeScreen("about_lessons_screen")
    data object AboutEvents: HomeScreen("about_events_screen")
    data object EventDetails: HomeScreen("event_details_screen/{$ARG_EVENT_ID}") {
        fun passId(id: String): String {
            return "event_details_screen/$id"
        }
    }
}

const val ARG_EVENT_ID = "idEvento"