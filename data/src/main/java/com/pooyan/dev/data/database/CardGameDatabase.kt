package com.pooyan.dev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pooyan.dev.data.database.dao.CardDao
import com.pooyan.dev.data.database.dao.PlayedCardDao
import com.pooyan.dev.data.database.entitiy.CardEntity
import com.pooyan.dev.data.database.entitiy.PlayedCardEntity

@Database(entities = [CardEntity::class, PlayedCardEntity::class], version = 1, exportSchema = true)
abstract class CardGameDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun playedCardDao(): PlayedCardDao
}