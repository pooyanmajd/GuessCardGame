package com.pooyan.dev.data.database

import android.content.Context
import androidx.room.Room
import com.pooyan.dev.data.database.dao.CardDao
import com.pooyan.dev.data.database.dao.PlayedCardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideLocalDataBase(@ApplicationContext context: Context): CardGameDatabase =
        Room.databaseBuilder(context, CardGameDatabase::class.java, "GuessCardGameDb")
            .fallbackToDestructiveMigration()
            .build()

    // Daos
    @Singleton
    @Provides
    internal fun provideCardDao(cardGameDatabase: CardGameDatabase): CardDao {
        return cardGameDatabase.cardDao()
    }

    @Singleton
    @Provides
    internal fun providePlayedCardDao(cardGameDatabase: CardGameDatabase): PlayedCardDao {
        return cardGameDatabase.playedCardDao()
    }
}