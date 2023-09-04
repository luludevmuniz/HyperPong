package com.alpaca.hyperpong.navigation

sealed class AuthScreen(val route: String) {
    data object Login: AuthScreen(LOGIN_SCREEN)
    data object Register: AuthScreen(REGISTER_SCREEN)
}

sealed class HomeScreen(val route: String) {
    data object Home: HomeScreen(HOME_SCREEN)
    data object Settings: HomeScreen(SETTINGS_SCREEN)
    data object AboutUs: HomeScreen(ABOUT_US_SCREEN)
    data object AboutLessons: HomeScreen(ABOUT_LESSONS_SCREEN)
    data object AboutEvents: HomeScreen(ABOUT_EVENTS_SCREEN)
    data object EventDetails: HomeScreen(EVENT_DETAILS_SCREEN) {
        fun passId(id: String): String {
            return "event_details_screen/$id"
        }
    }
}

const val ARG_EVENT_ID = "idEvento"
const val LOGIN_SCREEN = "login_screen"
const val REGISTER_SCREEN = "register_screen"
const val HOME_SCREEN = "home_screen"
const val SETTINGS_SCREEN = "settings_screen"
const val ABOUT_US_SCREEN = "about_us_screen"
const val ABOUT_LESSONS_SCREEN = "about_lessons_screen"
const val ABOUT_EVENTS_SCREEN = "about_events_screen"
const val EVENT_DETAILS_SCREEN = "event_details_screen/{$ARG_EVENT_ID}"
