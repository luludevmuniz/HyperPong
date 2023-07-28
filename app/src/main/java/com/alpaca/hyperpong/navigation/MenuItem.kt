package com.alpaca.hyperpong.navigation

import com.alpaca.hyperpong.R

sealed class MenuItem(val title: String, val iconId: Int, val screen: Screen) {
    object Home: MenuItem(
        title = "Home",
        iconId = R.drawable.ic_home,
        screen = Screen.Home
    )
    object SobreEventos: MenuItem(
        title = "Sobre os eventos",
        iconId = R.drawable.ic_event_info,
        screen = Screen.AboutEvents
    )
    object SobreAulas: MenuItem(
        title = "Sobre as aulas",
        iconId = R.drawable.ic_table,
        screen = Screen.AboutLessons
    )
    object SobreNos: MenuItem(
        title = "Sobre nós",
        iconId = R.drawable.ic_two_people,
        screen = Screen.AboutUs
    )
    object Configuracoes: MenuItem(
        title = "Configurações",
        iconId = R.drawable.ic_settings,
        screen = Screen.Settings
    )
    object Sair: MenuItem(
        title = "Sair",
        iconId = R.drawable.ic_close,
        screen = Screen.Login
    )
}
