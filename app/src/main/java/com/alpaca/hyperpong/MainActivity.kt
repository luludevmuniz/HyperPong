package com.alpaca.hyperpong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.alpaca.hyperpong.navigation.graph.Graph
import com.alpaca.hyperpong.navigation.graph.RootNavigationGraph
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
                val isUsuarioLogado by authViewModel.isUsuarioLogado.collectAsStateWithLifecycle()

                RootNavigationGraph(
                    navController = navController,
                    startDestination = if (isUsuarioLogado) Graph.HOME else Graph.AUTHENTICATION
                )
            }
        }
    }
}