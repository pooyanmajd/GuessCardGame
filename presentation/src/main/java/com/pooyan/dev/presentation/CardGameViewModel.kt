package com.pooyan.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pooyan.dev.domain.CompareCardsUseCase
import com.pooyan.dev.domain.GetCardsUseCase
import com.pooyan.dev.domain.ResetPlayedCardUseCase
import com.pooyan.dev.model.Card
import com.pooyan.dev.model.ComparedCardValue
import com.pooyan.dev.model.Guess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    fun onLowerClick() = handleGuess(false)

    fun onHigherClick() = handleGuess(true)

    fun startOver() {
        viewModelScope.launch {
            resetPlayedCard()
            val nextCard = getCardsUseCase()

            nextCard?.let { card ->
                uiStateEmitter.update {
                    it.copy(
                        currentCard = card,
                        gameOver = false,
                        guess = Guess.NOT_PLAYED,
                        numberOfCorrectGuess = 0,
                        lives = 3
                    )
                }
            }
        }
    }

    private fun startGame() {
        viewModelScope.launch {
            resetPlayedCard()
            val nextCard = getCardsUseCase()

            nextCard?.let {
                uiStateEmitter.update { it.copy(isLoading = false, currentCard = nextCard) }
            }
        }
    }

    private fun handleGuess(guessIsHigher: Boolean) {
        viewModelScope.launch {
            val currentCard = uiStateEmitter.value.currentCard
            val nextCard = getCardsUseCase()

            if (nextCard != null) {
                when (compareCardsUseCase(currentCard, nextCard)) {
                    ComparedCardValue.HIGHER -> if (guessIsHigher) guessCorrect(nextCard) else guessWrong(
                        nextCard
                    )
                    ComparedCardValue.EQUAL -> guessEqual(nextCard)
                    ComparedCardValue.LOWER -> if (guessIsHigher) guessWrong(nextCard) else guessCorrect(
                        nextCard
                    )
                }

            } else {
                // When we reach the end of the deck. We can improve that by adding a better logic to handle it instead of game over
                uiStateEmitter.update { it.copy(gameOver = true) }
            }
        }
    }

    private fun guessCorrect(nextCard: Card) {
        val numberOfCorrectGuess = uiStateEmitter.value.numberOfCorrectGuess + 1

        uiStateEmitter.update {
            it.copy(
                guess = Guess.CORRECT,
                numberOfCorrectGuess = numberOfCorrectGuess,
                currentCard = nextCard
            )
        }
    }

    // We don't have a logic to handle when guess is equal. For now we just show the next card without adding to correct guesses
    private fun guessEqual(nextCard: Card) {
        uiStateEmitter.update {
            it.copy(
                guess = Guess.CORRECT,
                currentCard = nextCard
            )
        }
    }

    private fun guessWrong(nextCard: Card) {
        val numberOfLives = uiStateEmitter.value.lives - 1

        if (numberOfLives == 0) {
            uiStateEmitter.update { it.copy(gameOver = true, lives = numberOfLives) }
        } else {
            uiStateEmitter.update {
                it.copy(
                    guess = Guess.WRONG,
                    lives = numberOfLives,
                    currentCard = nextCard
                )
            }
        }
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