package com.example.baubapchallenge.ui.navigation

sealed class BaupbapRoute(val route: String) {
    data object Welcome : BaupbapRoute("welcome")
    data object SignIn : BaupbapRoute("signIn")
    data object SignUp : BaupbapRoute("signUp")
    data object Home : BaupbapRoute("home")
}
