package com.example.covid19tracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.covid19tracker.home.HomeScreen
import com.example.covid19tracker.registration.SignInScreen
import com.example.covid19tracker.registration.SignUpScreen

@Composable
fun NavApp(modifier: Modifier,navController: NavHostController){
    NavHost(navController=navController, startDestination = "signin"){
        composable("signin"){
            SignInScreen(modifier,navController)
        }
        composable("signup"){
            SignUpScreen(modifier,navController)
        }
        composable("home"){
            HomeScreen()
        }
    }
}

