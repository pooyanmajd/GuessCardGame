package com.pooyan.dev.presentation

import com.pooyan.dev.domain.CompareCardsUseCase
import com.pooyan.dev.domain.GetCardsUseCase
import com.pooyan.dev.domain.ResetPlayedCardUseCase
import com.pooyan.dev.model.Card
import com.pooyan.dev.model.ComparedCardValue
import com.pooyan.dev.model.Guess
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class CardGameViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val compareCardsUseCase: CompareCardsUseCase = mockk()
    private val getCardsUseCase: GetCardsUseCase = mockk()
    private val resetPlayedCard: ResetPlayedCardUseCase = mockk()
    private lateinit var viewModel: CardGameViewModel

    @Test
    fun `given starting the game, when fetch a card, then update ui`() = runTest {
        coEvery { getCardsUseCase() } returns createCards().first()
        coJustRun { resetPlayedCard() }

        viewModel = CardGameViewModel(compareCardsUseCase, getCardsUseCase, resetPlayedCard)

        val uiState = viewModel.uiState.first()

        assertEquals(uiState.isLoading, false)
        assertEquals(uiState.currentCard, createCards().first())
        assertEquals(uiState.gameOver, false)
        assertEquals(uiState.guess, Guess.NOT_PLAYED)
        assertEquals(uiState.lives, 3)
        assertEquals(uiState.numberOfCorrectGuess, 0)
    }

    @Test
    fun `given guess is higher, when correct, then update ui with adding to number of correct guess`() =
        runTest {
            val card1 = mockk<Card>()
            coEvery { getCardsUseCase() } returns card1
            coJustRun { resetPlayedCard() }

            viewModel = CardGameViewModel(compareCardsUseCase, getCardsUseCase, resetPlayedCard)

            val card2 = mockk<Card>()
            coEvery { getCardsUseCase() } returns card2

            coEvery { compareCardsUseCase(card1, card2) } returns ComparedCardValue.HIGHER

            viewModel.onHigherClick()

            // Verify that the GetCardsUseCase was called twice
            coVerify(exactly = 2) { getCardsUseCase() }

            // Verify that the CompareCardsUseCase was called once
            coVerify { compareCardsUseCase(card1, card2) }


            // Verify that the UI state was updated correctly
            val expectedState = CardGameViewModel.CardGameUiState(
                currentCard = card2,
                gameOver = false,
                guess = Guess.CORRECT,
                numberOfCorrectGuess = 1,
                lives = 3
            )

            val actualState = viewModel.uiState.first()

            assertEquals(expectedState, actualState)
        }

    @Test
    fun `given guess is higher, when incorrect, then update ui with removing one live`() = runTest {
        val card1 = mockk<Card>()
        coEvery { getCardsUseCase() } returns card1
        coJustRun { resetPlayedCard() }

        viewModel = CardGameViewModel(compareCardsUseCase, getCardsUseCase, resetPlayedCard)

        val card2 = mockk<Card>()
        coEvery { getCardsUseCase() } returns card2

        coEvery { compareCardsUseCase(card1, card2) } returns ComparedCardValue.LOWER

        viewModel.onHigherClick()

        // Verify that the GetCardsUseCase was called twice
        coVerify(exactly = 2) { getCardsUseCase() }

        // Verify that the CompareCardsUseCase was called once
        coVerify { compareCardsUseCase(card1, card2) }


        // Verify that the UI state was updated correctly
        val expectedState = CardGameViewModel.CardGameUiState(
            currentCard = card2,
            gameOver = false,
            guess = Guess.WRONG,
            numberOfCorrectGuess = 0,
            lives = 2
        )

        val actualState = viewModel.uiState.first()

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `given guess is lower, when correct, then update ui with adding to number of correct guess`() =
        runTest {
            val card1 = mockk<Card>()
            coEvery { getCardsUseCase() } returns card1
            coJustRun { resetPlayedCard() }

            viewModel = CardGameViewModel(compareCardsUseCase, getCardsUseCase, resetPlayedCard)

            val card2 = mockk<Card>()
            coEvery { getCardsUseCase() } returns card2

            coEvery { compareCardsUseCase(card1, card2) } returns ComparedCardValue.LOWER

            viewModel.onLowerClick()

            // Verify that the GetCardsUseCase was called twice
            coVerify(exactly = 2) { getCardsUseCase() }

            // Verify that the CompareCardsUseCase was called once
            coVerify { compareCardsUseCase(card1, card2) }

            // Verify that the UI state was updated correctly
            val expectedState = CardGameViewModel.CardGameUiState(
                currentCard = card2,
                gameOver = false,
                guess = Guess.CORRECT,
                numberOfCorrectGuess = 1,
                lives = 3
            )

            val actualState = viewModel.uiState.first()

            assertEquals(expectedState, actualState)
        }

    @Test
    fun `given guess is lower, when incorrect, then update ui with removing one live`() = runTest {
        val card1 = mockk<Card>()
        coEvery { getCardsUseCase() } returns card1
        coJustRun { resetPlayedCard() }

        viewModel = CardGameViewModel(compareCardsUseCase, getCardsUseCase, resetPlayedCard)

        val card2 = mockk<Card>()
        coEvery { getCardsUseCase() } returns card2

        coEvery { compareCardsUseCase(card1, card2) } returns ComparedCardValue.HIGHER

        viewModel.onLowerClick()

        // Verify that the GetCardsUseCase was called twice
        coVerify(exactly = 2) { getCardsUseCase() }

        // Verify that the CompareCardsUseCase was called once
        coVerify { compareCardsUseCase(card1, card2) }

        // Verify that the UI state was updated correctly
        val expectedState = CardGameViewModel.CardGameUiState(
            currentCard = card2,
            gameOver = false,
            guess = Guess.WRONG,
            numberOfCorrectGuess = 0,
            lives = 2
        )

        val actualState = viewModel.uiState.first()

        assertEquals(expectedState, actualState)
    }

    private fun createCards() = listOf(
        Card(1, "A", "hearts"),
        Card(2, "king", "spades"),
        Card(3, "5", "clubs"),
        Card(4, "9", "diamonds"),
        Card(5, "10", "spades"),
        Card(6, "2", "diamonds"),
        Card(7, "queen", "hearts"),
    )
}