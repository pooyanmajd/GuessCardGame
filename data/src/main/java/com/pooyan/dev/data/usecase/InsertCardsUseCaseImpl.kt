package com.pooyan.dev.data.usecase

import com.pooyan.dev.domain.InsertCardsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class InsertCardsUseCaseImpl @Inject constructor() : InsertCardsUseCase {

    override suspend fun invoke(): Flow<Boolean> {
        //TODO add logic to save cards
       return flowOf(false)
    }
}