package com.pooyan.dev.data.usecase

import com.pooyan.dev.domain.InsertCardsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindsInsertCardsUseCase(insertCardsUseCaseImpl: InsertCardsUseCaseImpl): InsertCardsUseCase
}