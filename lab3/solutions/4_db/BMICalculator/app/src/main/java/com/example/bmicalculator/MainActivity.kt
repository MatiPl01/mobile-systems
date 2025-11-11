package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val nav = rememberNavController()
            val vm: BmiMeasurementViewModel = viewModel()

            NavHost(navController = nav, startDestination =
                Routes.MAIN) {
                composable(Routes.MAIN) {
                    MainScreen(
                        vm = vm,
                        onShowHistory = {
                            nav.navigate(Routes.HISTORY) }
                    )
                }
                composable(Routes.HISTORY) {
                    HistoryScreen(
                        vm = vm,
                        onBack = { nav.popBackStack() }
                    )
                }
            }
        }
    }
}
