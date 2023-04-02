package com.pooyan.dev.data.repository

import com.pooyan.dev.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun insertCards(): Flow<Boolean>

    suspend fun getCards(): List<Card>

    suspend fun getPlayedCardsId(): List<Int>

    suspend fun insertPlayedCard(id: Int)

    suspend fun clearPlayedCardIds()
}