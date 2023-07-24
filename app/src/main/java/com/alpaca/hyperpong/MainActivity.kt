package com.alpaca.hyperpong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.alpaca.hyperpong.navigation.Screen
import com.alpaca.hyperpong.navigation.SetupNavGraph
import com.alpaca.hyperpong.presentation.common.AuthViewModel
import com.alpaca.hyperpong.ui.theme.HyperPongTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//val auth = Firebase.auth
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val currentUser = auth.currentUser
        setContent {
            HyperPongTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = hiltViewModel()
                SetupNavGraph(
                    navController = navController,
                    startDestination = if (authViewModel.isUsuarioLogado) Screen.Home.route
                    else Screen.Login.route
                )
            }
        }
    }
}