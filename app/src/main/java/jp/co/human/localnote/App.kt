package jp.co.human.localnote

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App(){
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "login"){
        composable("login") {
            LoginScreen(onLogin = {nav.navigate("home")})
        }
        composable("home") {
            HomeScreen(onLogout = {nav.popBackStack() })
        }
    }
}