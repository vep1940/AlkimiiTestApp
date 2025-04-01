package com.vep1940.alkimiitestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vep1940.alkimiitestapp.presentation.screen.detail.detailScreen
import com.vep1940.alkimiitestapp.presentation.screen.detail.navigateToDetailScreen
import com.vep1940.alkimiitestapp.presentation.screen.list.Constant.LIST_SCREEN_ROUTE
import com.vep1940.alkimiitestapp.presentation.screen.list.listScreen
import com.vep1940.alkimiitestapp.presentation.theme.AlkimiiTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AlkimiiTestAppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeContentPadding()
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = LIST_SCREEN_ROUTE,
                    ) {
                        listScreen(
                            onClickItem = { id -> navController.navigateToDetailScreen(id) }
                        )
                        detailScreen()
                    }
                }
            }
        }
    }
}