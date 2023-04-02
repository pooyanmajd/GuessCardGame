package com.pooyan.dev.domain

import com.pooyan.dev.model.Card

interface GetCardsUseCase {

    suspend operator fun invoke(): Card?
}