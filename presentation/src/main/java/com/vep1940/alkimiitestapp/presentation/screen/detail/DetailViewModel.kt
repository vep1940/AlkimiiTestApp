package com.vep1940.alkimiitestapp.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vep1940.alkimiitestapp.domain.usecase.GetCharacterUseCase
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DetailScreenState
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.toPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val characterUseCase: GetCharacterUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val screenArgs = DetailScreenArgs(savedStateHandle)

    private val _screenState = MutableStateFlow<DetailScreenState>(DetailScreenState.Loading)
    val screenState: StateFlow<DetailScreenState> = _screenState.onStart {
        getCharacterData(screenArgs.id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = DetailScreenState.Loading
    )

    private fun getCharacterData(id: Int) {
        viewModelScope.launch {
            characterUseCase(id = id).fold(
                onSuccess = { data ->
                    _screenState.update { DetailScreenState.Idle(data.toPresentation()) }
                },
                onFailure = {
                    _screenState.update { DetailScreenState.Error }
                },
            )
        }
    }
}