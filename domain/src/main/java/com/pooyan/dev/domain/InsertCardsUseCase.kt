package com.pooyan.dev.domain

import kotlinx.coroutines.flow.Flow

interface InsertCardsUseCase {

    suspend operator fun invoke(): Flow<Boolean>
}