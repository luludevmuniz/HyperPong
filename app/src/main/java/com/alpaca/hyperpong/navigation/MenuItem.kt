package com.alpaca.hyperpong.navigation

import com.alpaca.hyperpong.R

sealed class MenuItem(val title: String, val iconId: Int, val route: String) {
    data object Home: MenuItem(
        title = "Home",
        iconId = R.drawable.ic_home,
        route = HomeScreen.Home.route
    )
    data object SobreEventos: MenuItem(
        title = "Sobre os eventos",
        iconId = R.drawable.ic_event_info,
        route = HomeScreen.AboutEvents.route
    )
    data object SobreAulas: MenuItem(
        title = "Sobre as aulas",
        iconId = R.drawable.ic_table,
        route = HomeScreen.AboutLessons.route
    )
    data object SobreNos: MenuItem(
        title = "Sobre nós",
        iconId = R.drawable.ic_two_people,
        route = HomeScreen.AboutUs.route
    )
    data object Configuracoes: MenuItem(
        title = "Configurações",
        iconId = R.drawable.ic_settings,
        route = HomeScreen.Settings.route
    )
    data object Sair: MenuItem(
        title = "Sair",
        iconId = R.drawable.ic_close,
        route = AuthScreen.Login.route
    )
}
