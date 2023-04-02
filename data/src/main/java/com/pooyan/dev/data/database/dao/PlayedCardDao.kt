package com.pooyan.dev.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pooyan.dev.data.database.entitiy.PlayedCardEntity

@Dao
interface PlayedCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayedCardId(playedCard: PlayedCardEntity)

    @Query("SELECT * FROM played_cards")
    suspend fun getPlayedCardsId(): List<PlayedCardEntity>

    @Query("DELETE FROM played_cards")
    suspend fun clear()
}