package com.pooyan.dev.data.mapper

import com.pooyan.dev.data.database.entitiy.CardEntity
import com.pooyan.dev.data.network.CardDto
import com.pooyan.dev.model.Card

fun CardDto.toCardEntity() = CardEntity(value = value, suit = suit)

fun CardEntity.toExternal() = Card(id = id, value = value, suit = suit)