package com.alpaca.hyperpong.presentation.screens.login

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.alpaca.hyperpong.auth
import com.alpaca.hyperpong.navigation.Screen

@Composable
fun LoginScreen(navController: NavHostController) {
    val activity = LocalContext.current as Activity
    LoginContent(navController) { email, senha ->
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    navController.navigate(Screen.Home.route)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        activity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}