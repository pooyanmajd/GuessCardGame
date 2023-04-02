package com.pooyan.dev.data.dispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provide IO dispatcher to inject
 */
@Module
@InstallIn(SingletonComponent::class)
object GuessCardGameDispatchersModule {

    @Provides
    @Dispatcher(GuessCardGameDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}