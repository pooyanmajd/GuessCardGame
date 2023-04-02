package com.pooyan.dev.data.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "played_cards")
data class PlayedCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cardId: Int,
)