package com.pooyan.dev.data.repository

import kotlinx.coroutines.flow.Flow

interface CardRepository {

    fun insertCards(): Flow<Boolean>
}