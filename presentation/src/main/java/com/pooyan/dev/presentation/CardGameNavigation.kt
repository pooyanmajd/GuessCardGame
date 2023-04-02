package com.pooyan.dev.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val cardGameNavigationRoute = "card_game_route"

fun NavGraphBuilder.cardGameScreen() {
    composable(route = cardGameNavigationRoute) {
        CardGameScreen()
    }
}