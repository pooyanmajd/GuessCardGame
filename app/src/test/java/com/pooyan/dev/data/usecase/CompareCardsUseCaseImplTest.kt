package com.pooyan.dev.data.usecase

import com.pooyan.dev.model.Card
import com.pooyan.dev.model.ComparedCardValue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class CompareCardsUseCaseImplTest {

    private lateinit var compareCardsUseCaseImpl: CompareCardsUseCaseImpl

    @Before
    fun setUp() {
        compareCardsUseCaseImpl = CompareCardsUseCaseImpl()
    }

    @Test
    fun `when current card value is less than next card value, return HIGHER`() {
        val currentCard = Card(id = 1, value = "a", suit = "diamonds")
        val nextCard = Card(id = 2, value = "3", suit = "hearts")
        val result = runBlocking { compareCardsUseCaseImpl.invoke(currentCard, nextCard) }

        assertEquals(ComparedCardValue.HIGHER, result)
    }

    @Test
    fun `when current card value is greater than next card value, return LOWER`() {
        val currentCard = Card(id = 1, value = "king", suit = "diamonds")
        val nextCard = Card(id = 2, value = "jack", suit = "hearts")
        val result = runBlocking { compareCardsUseCaseImpl.invoke(currentCard, nextCard) }

        assertEquals(ComparedCardValue.LOWER, result)
    }


    @Test
    fun `when current card value is equal to next card value, return EQUAL`() {
        val currentCard = Card(id = 1, value = "10", suit = "diamonds")
        val nextCard = Card(id = 2, value = "10", suit = "hearts")
        val result = runBlocking { compareCardsUseCaseImpl.invoke(currentCard, nextCard) }
        assertEquals(ComparedCardValue.EQUAL, result)
    }
}