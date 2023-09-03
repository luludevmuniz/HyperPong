package com.alpaca.hyperpong.navigation

import com.alpaca.hyperpong.R

sealed class MenuItem(val title: String, val iconId: Int, val route: String) {
    object Home: MenuItem(
        title = "Home",
        iconId = R.drawable.ic_home,
        route = HomeScreen.Home.route
    )
    object SobreEventos: MenuItem(
        title = "Sobre os eventos",
        iconId = R.drawable.ic_event_info,
        route = HomeScreen.AboutEvents.route
    )
    object SobreAulas: MenuItem(
        title = "Sobre as aulas",
        iconId = R.drawable.ic_table,
        route = HomeScreen.AboutLessons.route
    )
    object SobreNos: MenuItem(
        title = "Sobre nós",
        iconId = R.drawable.ic_two_people,
        route = HomeScreen.AboutUs.route
    )
    object Configuracoes: MenuItem(
        title = "Configurações",
        iconId = R.drawable.ic_settings,
        route = HomeScreen.Settings.route
    )
    object Sair: MenuItem(
        title = "Sair",
        iconId = R.drawable.ic_close,
        route = AuthScreen.Login.route
    )
}
