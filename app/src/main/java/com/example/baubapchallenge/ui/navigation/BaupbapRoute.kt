package com.example.baubapchallenge.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.baubapchallenge.core.extensions.empty

sealed class BaupbapRoute(val route: String) {
    data object Welcome : BaupbapRoute("welcome")
    data object SignIn : BaupbapRoute("signIn")
    data object SignUp : BaupbapRoute("signUp")
    data object Home : BaupbapRoute("home/{$USER_ID_ARGUMENT}") {

        fun createRoute(userId: String): String {
            return route.replace("{$USER_ID_ARGUMENT}", userId)
        }

        fun getArguments(defaultId: String = String.empty()) = listOf(
            navArgument(USER_ID_ARGUMENT) {
                type = NavType.StringType
                defaultValue = defaultId
            }
        )
    }

    companion object {
        const val USER_ID_ARGUMENT = "userId"
    }
}
