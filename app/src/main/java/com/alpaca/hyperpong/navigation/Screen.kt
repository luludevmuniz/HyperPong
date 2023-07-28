package com.alpaca.hyperpong.navigation

sealed class Screen(val route: String) {
    object Login: Screen("login_screen")
    object Register: Screen("register_screen")
    object Home: Screen("home_screen")
    object Settings: Screen("settings_screen")
    object AboutUs: Screen("about_us_screen")
    object AboutLessons: Screen("about_lessons_screen")
    object AboutEvents: Screen("about_events_screen")
}
