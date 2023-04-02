package com.pooyan.dev.guesscardgame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pooyan.dev.presentation.cardGameNavigationRoute
import com.pooyan.dev.presentation.cardGameScreen

/**
 * A composable nav host to create screens of the app
 * Each screen will handle it's own logic
 */
@Composable
fun GuessCardGameNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = cardGameNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        cardGameScreen()
    }
}