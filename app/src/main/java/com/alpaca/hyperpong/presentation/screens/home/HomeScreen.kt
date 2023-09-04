package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.navigation.HomeScreen
import com.alpaca.hyperpong.navigation.MenuItem.Configuracoes
import com.alpaca.hyperpong.navigation.MenuItem.Home
import com.alpaca.hyperpong.navigation.MenuItem.Sair
import com.alpaca.hyperpong.navigation.MenuItem.SobreAulas
import com.alpaca.hyperpong.navigation.MenuItem.SobreEventos
import com.alpaca.hyperpong.navigation.MenuItem.SobreNos
import com.alpaca.hyperpong.navigation.graph.HomeNavGraph
import com.alpaca.hyperpong.presentation.shared.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onUserDisconnected: () -> Unit
) {
    val isUsuarioLogado by authViewModel.isUsuarioLogado.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val menuItems = listOf(
        Home,
        SobreEventos,
        SobreAulas,
        SobreNos,
        Configuracoes,
        Sair
    )
    val showLogoutAlert = remember { mutableStateOf(false) }
    var selectedMenuItem by remember { mutableStateOf(menuItems[0]) }

    if (!isUsuarioLogado) {
        LaunchedEffect(Unit) {
            onUserDisconnected()
        }
    }
    authViewModel.getEventos()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                menuItems.forEach { menuItem ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        icon = {
                            Icon(
                                painter = painterResource(id = menuItem.iconId),
                                contentDescription = menuItem.title
                            )
                        },
                        label = { Text(menuItem.title) },
                        selected = menuItem == selectedMenuItem,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedMenuItem = menuItem
                            if (menuItem == Sair) {
                                showLogoutAlert.value = true
                            } else {
                                navController.navigate(menuItem.route) {
                                    popUpTo(route = HomeScreen.Home.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {
        HomeNavGraph(navController = navController) {
            scope.launch {
                if (drawerState.isOpen) drawerState.close() else drawerState.open()
            }
        }
    }

    if (showLogoutAlert.value) {
        AlertDialog(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "Alerta"
                )
            },
            title = { Text(text = "Sair da sua conta?") },
            text = {
                Text(text = "Você está prestes a se desconectar da conta sua conta. Deseja confirmar?")
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showLogoutAlert.value = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            },
            onDismissRequest = {
                showLogoutAlert.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        authViewModel.deslogarUsuario()
                        showLogoutAlert.value = false
                    }
                ) {
                    Text(text = "Sim")
                }
            }
        )
    }
}