package com.pooyan.dev.data.repository

import com.pooyan.dev.data.database.dao.CardDao
import com.pooyan.dev.data.database.dao.PlayedCardDao
import com.pooyan.dev.data.database.entitiy.PlayedCardEntity
import com.pooyan.dev.data.dispatcher.Dispatcher
import com.pooyan.dev.data.dispatcher.GuessCardGameDispatchers
import com.pooyan.dev.data.mapper.toCardEntity
import com.pooyan.dev.data.mapper.toExternal
import com.pooyan.dev.data.network.GuessCardGameApiService
import com.pooyan.dev.model.Card
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [CardRepository] that communicates with the local database and the remote API
 * to handle data related to the card game.
 * @param ioDispatcher [CoroutineDispatcher] to perform database and network operations on the I/O
 * @param apiService [GuessCardGameApiService] instance to make API calls to get the cards.
 * @param cardDao [CardDao] instance to perform database operations related to cards.
 * @param playedCardDao [PlayedCardDao] instance to perform database operations related to played cards.
 */
@Singleton
class CardRepositoryImpl @Inject constructor(
    @Dispatcher(GuessCardGameDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val apiService: GuessCardGameApiService,
    private val cardDao: CardDao,
    private val playedCardDao: PlayedCardDao
) : CardRepository {

    override fun insertCards(): Flow<Boolean> {
        return flow {
            val currentCardTableCount = cardDao.getCardsCount()
            if (currentCardTableCount == 0) {
                val cards = apiService.getCards()
                val cardEntity = cards.map {
                    it.toCardEntity()
                }
                cardDao.insertCards(cardEntity)
            }
            emit(true)
        }.catch {
            emit(false)
        }.flowOn(ioDispatcher)
    }

    override suspend fun getCards(): List<Card> = withContext(ioDispatcher) {
        val cards = cardDao.getCards()
        cards.map { it.toExternal() }
    }

    override suspend fun getPlayedCardsId(): List<Int> = withContext(ioDispatcher) {
        val playedCards = playedCardDao.getPlayedCardsId()
        playedCards.map { it.cardId }
    }

    override suspend fun insertPlayedCard(id: Int) = withContext(ioDispatcher) {
        playedCardDao.insertPlayedCardId(PlayedCardEntity(cardId = id))
    }

    override suspend fun clearPlayedCardIds() = withContext(ioDispatcher) {
        playedCardDao.clear()
    }
}