package com.vep1940.alkimiitestapp.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vep1940.alkimiitestapp.domain.usecase.AddCharacterToFavsUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetCharacterUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetFavouriteCharacterUseCase
import com.vep1940.alkimiitestapp.domain.usecase.RemoveCharacterFromFavsUseCase
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DataStatus
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DetailScreenState
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.toPresentation
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val addCharacterToFavsUseCase: AddCharacterToFavsUseCase,
    private val removeCharacterFromFavsUseCase: RemoveCharacterFromFavsUseCase,
    getFavouriteCharacterUseCase: GetFavouriteCharacterUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val screenArgs = DetailScreenArgs(savedStateHandle)

    private val _screenState = MutableStateFlow(DetailScreenState.makeInitialState())
    val screenState: StateFlow<DetailScreenState> = combine(
        _screenState,
        getFavouriteCharacterUseCase(screenArgs.id)
    ) { uiState, isFavCharacter ->
        uiState.copy(
            data = uiState.data?.copy(isFavourite = isFavCharacter)
        )
    }.onStart {
        getCharacterData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = DetailScreenState.makeInitialState()
    )

    private var characterJob: Job? = null

    fun getCharacterData() {
        characterJob?.cancel()
        characterJob = viewModelScope.launch {
            _screenState.update { oldState -> oldState.copy(dataStatus = DataStatus.LOADING) }
            getCharacterUseCase(id = screenArgs.id).fold(
                onSuccess = { data ->
                    _screenState.update { oldState ->
                        oldState.copy(
                            data = data.toPresentation(),
                            dataStatus = DataStatus.IDLE,
                        )
                    }
                },
                onFailure = {
                    _screenState.update { oldState ->
                        oldState.copy(dataStatus = DataStatus.ERROR)
                    }
                },
            )
        }
    }

    fun onClickFav() {
        screenState.value.data?.isFavourite?.let { isFavourite ->
            if (isFavourite) {
                removeCharacterFromFavsUseCase(characterId = screenArgs.id)
            } else {
                addCharacterToFavsUseCase(characterId = screenArgs.id)
            }
        }
    }
}