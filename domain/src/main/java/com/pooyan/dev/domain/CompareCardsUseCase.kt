package com.pooyan.dev.domain

import com.pooyan.dev.model.Card
import com.pooyan.dev.model.ComparedCardValue

/**
 * An interface that defines a use case for comparing two [Card] objects based on their values.
 * A is the lowest and king is the highest
 */
interface CompareCardsUseCase {

    suspend operator fun invoke(currentCard: Card, nextCard: Card): ComparedCardValue
}