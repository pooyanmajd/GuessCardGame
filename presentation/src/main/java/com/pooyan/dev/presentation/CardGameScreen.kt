package com.pooyan.dev.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pooyan.dev.model.Guess.CORRECT
import com.pooyan.dev.model.Guess.NOT_PLAYED
import com.pooyan.dev.model.Guess.WRONG

@Composable
fun CardGameScreen(
    modifier: Modifier = Modifier,
    viewModel: CardGameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CardGameContent(
        modifier = modifier,
        uiState = uiState,
        onHigherClick = viewModel::onHigherClick,
        onLowerClick = viewModel::onLowerClick,
        onStartOver = viewModel::startOver
    )
}

@Composable
private fun CardGameContent(
    modifier: Modifier,
    uiState: CardGameViewModel.CardGameUiState,
    onHigherClick: () -> Unit,
    onLowerClick: () -> Unit,
    onStartOver: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            LoadingScreen(modifier = modifier)
        } else {
            CardGameSuccess(
                modifier = modifier,
                uiState = uiState,
                onHigherClick = onHigherClick,
                onLowerClick = onLowerClick,
                onStartOver = onStartOver
            )
        }
    }
}

@Composable
private fun CardGameSuccess(
    modifier: Modifier,
    uiState: CardGameViewModel.CardGameUiState,
    onHigherClick: () -> Unit,
    onLowerClick: () -> Unit,
    onStartOver: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (uiState.gameOver) {
            GameOver(modifier, uiState, onStartOver)

        } else {
            GameOn(
                modifier = modifier,
                uiState = uiState,
                onHigherClick = onHigherClick,
                onLowerClick = onLowerClick
            )
        }
    }
}

@Composable
private fun GameOver(
    modifier: Modifier,
    uiState: CardGameViewModel.CardGameUiState,
    onStartOver: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = stringResource(R.string.game_over),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = stringResource(id = R.string.your_result, uiState.numberOfCorrectGuess),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        )

        Button(modifier = modifier.padding(top = 16.dp), onClick = onStartOver) {
            Text(text = stringResource(R.string.start_over))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ColumnScope.GameOn(
    modifier: Modifier,
    uiState: CardGameViewModel.CardGameUiState,
    onHigherClick: () -> Unit,
    onLowerClick: () -> Unit
) {
    Text(text = stringResource(R.string.current_card), style = MaterialTheme.typography.titleLarge)

    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        text = "${uiState.currentCard.value} of ${uiState.currentCard.suit}",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.tertiary
    )

    Text(
        modifier = modifier.padding(top = 8.dp),
        text = stringResource(R.string.how_to_play)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onHigherClick) {
            Text(text = stringResource(R.string.higher))
        }

        Button(onClick = onLowerClick) {
            Text(text = stringResource(R.string.lower))
        }
    }
    AnimatedContent(targetState = uiState.numberOfCorrectGuess) { targetGuess ->
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(id = R.string.number_of_guess, targetGuess),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.tertiary
        )
    }

    AnimatedContent(targetState = uiState.lives) { targetLives ->
        Text(
            modifier = modifier
                .fillMaxWidth(), text = stringResource(id = R.string.number_of_lives, targetLives),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.tertiary
        )
    }

    Message(modifier = modifier, uiState = uiState)
}

@Composable
private fun ColumnScope.Message(
    modifier: Modifier,
    uiState: CardGameViewModel.CardGameUiState
) {
    Spacer(modifier = modifier.weight(1F))

    when (uiState.guess) {
        CORRECT -> MessageText(
            modifier = modifier,
            text = stringResource(R.string.guess_right),
            isCorrect = true
        )
        NOT_PLAYED -> MessageText(
            modifier = modifier,
            text = stringResource(R.string.good_luck),
            isCorrect = true
        )
        WRONG -> MessageText(
            modifier = modifier,
            text = stringResource(R.string.guess_wrong),
            isCorrect = false
        )
    }
}

@Composable
private fun MessageText(modifier: Modifier, text: String, isCorrect: Boolean) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = if (isCorrect) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center
    )
}
