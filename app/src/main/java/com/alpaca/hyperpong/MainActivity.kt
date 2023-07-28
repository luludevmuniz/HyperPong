package com.alpaca.hyperpong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.alpaca.hyperpong.navigation.Screen
import com.alpaca.hyperpong.navigation.SetupNavGraph
import com.alpaca.hyperpong.presentation.shared.AuthViewModel
import com.alpaca.hyperpong.ui.theme.HyperPongTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HyperPongTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = hiltViewModel()
                val isUsuarioLogado by remember { mutableStateOf(authViewModel.isUsuarioLogado.value) }
                SetupNavGraph(
                    navController = navController,
                    startDestination = if (isUsuarioLogado) Screen.Home.route
                    else Screen.Login.route
                )
            }
        }
    }
}