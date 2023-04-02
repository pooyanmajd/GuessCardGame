package com.pooyan.dev.data.usecase

import com.pooyan.dev.domain.CompareCardsUseCase
import com.pooyan.dev.model.Card
import com.pooyan.dev.model.ComparedCardValue
import javax.inject.Inject

/**
 * Compares two [Card] objects based on their values and returns a [ComparedCardValue] enum.
 * @param currentCard The [Card] object to compare against.
 * @param nextCard The [Card] object to compare to.
 * @return A [ComparedCardValue] enum representing the result of the comparison.
 */
class CompareCardsUseCaseImpl @Inject constructor() : CompareCardsUseCase {
    override suspend fun invoke(currentCard: Card, nextCard: Card): ComparedCardValue {
        val cardValues =
            listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king")
        val currentValueIndex = cardValues.indexOf(currentCard.value)
        val nextValueIndex = cardValues.indexOf(nextCard.value)

        return when {
            currentValueIndex < nextValueIndex -> ComparedCardValue.HIGHER // nextCard is higher
            currentValueIndex > nextValueIndex -> ComparedCardValue.LOWER // currentCard is higher
            else -> ComparedCardValue.EQUAL // the cards have the same value
        }
    }
}