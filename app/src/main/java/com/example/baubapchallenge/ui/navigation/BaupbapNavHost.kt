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
        homeScreenNav()
    }
}

private fun NavGraphBuilder.welcomeScreenNav(navController: NavHostController) {
    composable(route = BaupbapRoute.Welcome.route) {
        WelcomeScreen(
            onSignUp = { navController.navigate(BaupbapRoute.SignUp.route) },
            onSignIn = { navController.navigate(BaupbapRoute.SignIn.route) }
        )
    }
}

private fun NavGraphBuilder.signUpScreenNav(navController: NavHostController) {
    composable(route = BaupbapRoute.SignUp.route) {
        SignUpScreen(
            onConsultCurp = { },
            onSignIn = {
                navController.navigate(BaupbapRoute.SignIn.route) {
                    popUpTo(BaupbapRoute.SignUp.route) { inclusive = true }
                }
            },
            onHome = {
                navController.navigate(BaupbapRoute.Home.createRoute(it)) {
                    popUpTo(0) { inclusive = true }
                }
            },
            onBack = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.signInScreenNav(navController: NavHostController) {
    composable(route = BaupbapRoute.SignIn.route) {
        SignInScreen(
            onSingUp = {
                navController.navigate(BaupbapRoute.SignUp.route) {
                    popUpTo(BaupbapRoute.SignIn.route) { inclusive = true }
                }
            },
            onHome = {
                navController.navigate(BaupbapRoute.Home.createRoute(it)) {
                    popUpTo(0) { inclusive = true }
                }
            },
            onBack = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.homeScreenNav() {
    composable(
        route = BaupbapRoute.Home.route,
        arguments = BaupbapRoute.Home.getArguments()
    ) {
        HomeScreen(userId = it.arguments?.getString(BaupbapRoute.USER_ID_ARGUMENT).orEmpty())
    }
}
