package com.pooyan.dev.presentation

import androidx.lifecycle.ViewModel
import com.pooyan.dev.domain.CompareCardsUseCase
import com.pooyan.dev.domain.GetCardsUseCase
import com.pooyan.dev.domain.ResetPlayedCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardGameViewModel @Inject constructor(
    private val compareCardsUseCase: CompareCardsUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val resetPlayedCard: ResetPlayedCardUseCase
) : ViewModel() {
}