package com.pooyan.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pooyan.dev.domain.CompareCardsUseCase
import com.pooyan.dev.domain.GetCardsUseCase
import com.pooyan.dev.domain.ResetPlayedCardUseCase
import com.pooyan.dev.model.Card
import com.pooyan.dev.model.Guess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CardGameViewModel @Inject constructor(
    private val compareCardsUseCase: CompareCardsUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val resetPlayedCard: ResetPlayedCardUseCase
) : ViewModel() {

    private val uiStateEmitter = MutableStateFlow(CardGameUiState())
    val uiState: StateFlow<CardGameUiState> = uiStateEmitter
        .stateIn(
            scope = viewModelScope,
            initialValue = CardGameUiState(isLoading = true),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        startGame()
    }

    private fun startGame() {

    }

    data class CardGameUiState(
        val isLoading: Boolean = false,
        val currentCard: Card = Card(id = 0, value = "", suit = ""),
        val gameOver: Boolean = false,
        val guess: Guess = Guess.NOT_PLAYED,
        val lives: Int = 3,
        val numberOfCorrectGuess: Int = 0
    )
}