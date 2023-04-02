package com.pooyan.dev.data.usecase

import com.pooyan.dev.data.repository.CardRepository
import com.pooyan.dev.domain.ResetPlayedCardUseCase
import javax.inject.Inject

/**
 * A use case implementation for resetting played card ids in the repository.
 * @property cardRepository The repository for accessing and manipulating card data.
 */
class ResetPlayedCardUseCaseImpl @Inject constructor(private val cardRepository: CardRepository) :
    ResetPlayedCardUseCase {

    override suspend fun invoke() {
        return cardRepository.clearPlayedCardIds()
    }
}