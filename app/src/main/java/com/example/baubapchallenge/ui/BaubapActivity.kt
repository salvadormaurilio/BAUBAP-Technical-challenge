package com.example.baubapchallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.baubapchallenge.ui.navigation.BaupbapNavHost
import com.example.baubapchallenge.ui.theme.BaubapChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaubapChallengeTheme {
                val navHostController = rememberNavController()
                BaupbapNavHost(navHostController = navHostController)
            }
        }
    }
}
