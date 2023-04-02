package com.pooyan.dev.guesscardgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pooyan.dev.domain.InsertCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(insertCardsUseCase: InsertCardsUseCase) :
    ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = insertCardsUseCase().map {
        if (it) {
            MainActivityUiState.Success(it)
        } else {
            MainActivityUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000), // wait 5 seconds when configuration changed to not reload the whole page
    )

    sealed interface MainActivityUiState {
        object Loading : MainActivityUiState
        data class Success(val dateLoaded: Boolean) : MainActivityUiState
        object Error : MainActivityUiState
    }
}