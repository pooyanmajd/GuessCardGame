package com.pooyan.dev.presentation

import androidx.lifecycle.ViewModel
import com.pooyan.dev.domain.CompareCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardGameViewModel @Inject constructor(private val compareCardsUseCase: CompareCardsUseCase) :
    ViewModel() {
}