package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BackdropScaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BackdropValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.navigation.MenuItem.Configuracoes
import com.alpaca.hyperpong.navigation.MenuItem.Home
import com.alpaca.hyperpong.navigation.MenuItem.Sair
import com.alpaca.hyperpong.navigation.MenuItem.SobreAulas
import com.alpaca.hyperpong.navigation.MenuItem.SobreEventos
import com.alpaca.hyperpong.navigation.MenuItem.SobreNos
import com.alpaca.hyperpong.navigation.Screen
import com.alpaca.hyperpong.presentation.shared.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isUsuarioLogado by authViewModel.isUsuarioLogado.collectAsState()
    var selectedTab by remember {
        mutableStateOf(HomeTab.Eventos)
    }
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
            navegarParaDestino(
                navController = navController,
                route = Screen.Login.route
            )
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = item == selectedMenuItem,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedMenuItem = item
                            if (item == Sair) {
                                showLogoutAlert.value = true
                            } else {
                                navegarParaDestino(navController, item.screen.route)
                            }
                        }
                    )
                }
            }
        }
    ) {
        BackdropScaffold(
            scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
            appBar = {
                HomeTopBar {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }
            },
            frontLayerScrimColor = Color.Unspecified,
            backLayerBackgroundColor = MaterialTheme.colorScheme.surface,
            backLayerContent = {
                HomeBackLayer(
                    selectedTab = selectedTab,
                    onTabSelected = { newSelectedTab ->
                        selectedTab = newSelectedTab
                    }
                )
            },
            frontLayerContent = {
                when (selectedTab) {
                    HomeTab.Eventos -> HomeFrontLayer(eventos = eventos, categoria = selectedTab)
                    HomeTab.Aulas -> HomeFrontLayer(aulas = aulas, categoria = selectedTab)
                }
            }
        )
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

private fun navegarParaDestino(
    navController: NavHostController,
    route: String
) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}