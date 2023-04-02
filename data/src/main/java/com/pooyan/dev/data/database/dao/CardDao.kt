package com.pooyan.dev.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pooyan.dev.data.database.entitiy.CardEntity

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CardEntity>)

    @Query("SELECT COUNT(*) FROM cards")
    suspend fun getCardsCount(): Int

    @Query("SELECT * FROM cards")
    suspend fun getCards(): List<CardEntity>
}