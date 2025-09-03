package com.example.baubapchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baubapchallenge.ui.home.HomeScreen
import com.example.baubapchallenge.ui.signin.SignInScreen
import com.example.baubapchallenge.ui.signup.SignUpScreen
import com.example.baubapchallenge.ui.welcome.WelcomeScreen

@Composable
fun BaupbapNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BaupbapRoute.Welcome.route
    ) {
        welcomeScreenNav(navHostController)
        signUpScreenNav(navHostController)
        signInScreenNav(navHostController)
        homeScreenNav(navHostController)
    }
}

private fun NavGraphBuilder.welcomeScreenNav(navController: NavHostController) {
    composable(route = BaupbapRoute.Welcome.route) {
        WelcomeScreen()
    }
}

private fun NavGraphBuilder.signUpScreenNav(navController: NavHostController) {
    composable(route = BaupbapRoute.SignUp.route) {
        SignUpScreen()
    }
}

private fun NavGraphBuilder.signInScreenNav(navController: NavHostController) {
    composable(route = BaupbapRoute.SignIn.route) {
        SignInScreen()
    }
}

private fun NavGraphBuilder.homeScreenNav(navController: NavHostController) {
    composable(route = BaupbapRoute.Home.route) {
        HomeScreen()
    }
}
