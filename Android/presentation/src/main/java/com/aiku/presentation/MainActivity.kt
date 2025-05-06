package com.aiku.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.aiku.domain.usecase.LoginUseCase
import com.aiku.presentation.navigation.AikuNavigation
import com.aiku.presentation.navigation.route.Routes
import com.aiku.presentation.theme.AiKUTheme
import com.aiku.presentation.ui.screen.home.composable.HomeScreen
import com.aiku.presentation.ui.screen.login.composable.LoginScreen
import com.aiku.presentation.ui.screen.schedule.MyScheduleScreen
import com.aiku.presentation.ui.screen.splash.composable.SplashScreen

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var loginUseCase: LoginUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AiKUTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AikuNavigation(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        loginUseCase = loginUseCase
                    )
                }
            }
        }
    }
}
