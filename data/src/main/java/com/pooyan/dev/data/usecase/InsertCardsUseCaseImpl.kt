package com.pooyan.dev.data.usecase

import com.pooyan.dev.data.repository.CardRepository
import com.pooyan.dev.domain.InsertCardsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertCardsUseCaseImpl @Inject constructor(private val cardRepository: CardRepository) :
    InsertCardsUseCase {

    override fun invoke(): Flow<Boolean> {
        return cardRepository.insertCards()
    }
}