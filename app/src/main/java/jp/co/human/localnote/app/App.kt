package jp.co.human.localnote.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.co.human.localnote.ui.screen.HomeScreen
import jp.co.human.localnote.ui.screen.LoginScreen
import jp.co.human.localnote.ui.screen.RegisterScreen

@Composable
fun App(){
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "login"){
        composable("login") {
            LoginScreen(
                onLogin = { nav.navigate("home") },
                onRegister = { nav.navigate("register")}
            )
        }
        composable("home") {
            HomeScreen(onLogout = { nav.popBackStack() })
        }

        composable("register") {
            RegisterScreen(onDone = {nav.popBackStack()})
        }
    }
}