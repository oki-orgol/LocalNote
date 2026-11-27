package jp.co.human.localnote.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.co.human.localnote.ui.screen.HomeScreen
import jp.co.human.localnote.ui.screen.LoginScreen
import jp.co.human.localnote.ui.screen.ProfileScreen
import jp.co.human.localnote.ui.screen.RegisterScreen

@Composable
fun App(){
    val nav = rememberNavController()
    val userId = rememberSaveable { mutableStateOf("") }
    NavHost(navController = nav, startDestination = "login"){
        composable("login") {
            LoginScreen(
                onLogin = { id ->
                    userId.value = id
                    nav.navigate("home") {
                        popUpTo("login") { inclusive = true}
                    }
                },
                onRegister = { nav.navigate("register")}
            )
        }
        composable("home") {
            HomeScreen(
                userId = userId.value,
                onLogout = {
                userId.value = ""
                nav.navigate("login") {
                    popUpTo("home") { inclusive = true}
                }
            },
            onProfile = {
                nav.navigate("profile")
            })
        }
        composable("register") {
            RegisterScreen(onDone = {nav.popBackStack()})
        }
        composable("profile"){
            ProfileScreen(userId.value,
                onBack = { nav.popBackStack() })
        }
    }
}