package com.pooyan.dev.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CardGameScreen(
    modifier: Modifier = Modifier,
    viewModel: CardGameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CardGameContent(
        modifier = modifier,
        uiState = uiState
    )
}

@Composable
private fun CardGameContent(
    modifier: Modifier,
    uiState: CardGameViewModel.CardGameUiState,
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            LoadingScreen(modifier = modifier)
        } else {
            CardGameSuccess(
                modifier = modifier,
                uiState = uiState
            )
        }
    }
}

@Composable
private fun CardGameSuccess(
    modifier: Modifier,
    uiState: CardGameViewModel.CardGameUiState
) {

}