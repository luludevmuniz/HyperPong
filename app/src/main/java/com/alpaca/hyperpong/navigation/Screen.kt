package com.alpaca.hyperpong.navigation

sealed class AuthScreen(val route: String) {
    object Login: AuthScreen("login_screen")
    object Register: AuthScreen("register_screen")
}

sealed class HomeScreen(val route: String) {
    object Home: HomeScreen("home_screen")
    object Settings: HomeScreen("settings_screen")
    object AboutUs: HomeScreen("about_us_screen")
    object AboutLessons: HomeScreen("about_lessons_screen")
    object AboutEvents: HomeScreen("about_events_screen")
    object EventDetails: HomeScreen("event_details_screen")
}