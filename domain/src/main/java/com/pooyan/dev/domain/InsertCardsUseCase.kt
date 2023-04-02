package com.pooyan.dev.domain

import kotlinx.coroutines.flow.Flow

interface InsertCardsUseCase {

    operator fun invoke(): Flow<Boolean>
}