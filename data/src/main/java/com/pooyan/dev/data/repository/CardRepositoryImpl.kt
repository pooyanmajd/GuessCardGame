package com.pooyan.dev.data.repository

import com.pooyan.dev.data.database.dao.CardDao
import com.pooyan.dev.data.dispatcher.Dispatcher
import com.pooyan.dev.data.dispatcher.GuessCardGameDispatchers
import com.pooyan.dev.data.mapper.toCardEntity
import com.pooyan.dev.data.network.GuessCardGameApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    @Dispatcher(GuessCardGameDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val apiService: GuessCardGameApiService,
    private val cardDao: CardDao
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
}