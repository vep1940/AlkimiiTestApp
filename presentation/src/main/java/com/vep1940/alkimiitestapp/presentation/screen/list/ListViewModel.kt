package com.vep1940.alkimiitestapp.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vep1940.alkimiitestapp.domain.model.CharacterPage
import com.vep1940.alkimiitestapp.domain.usecase.AddCharacterToFavsUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetCharactersUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetFavouriteCharactersUseCase
import com.vep1940.alkimiitestapp.domain.usecase.RemoveCharacterFromFavsUseCase
import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay
import com.vep1940.alkimiitestapp.presentation.model.toDomain
import com.vep1940.alkimiitestapp.presentation.screen.list.model.CharacterListItemDisplay
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenState
import com.vep1940.alkimiitestapp.presentation.screen.list.model.PaginationState
import com.vep1940.alkimiitestapp.presentation.screen.list.model.toPresentation
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addCharacterToFavsUseCase: AddCharacterToFavsUseCase,
    private val removeCharacterFromFavsUseCase: RemoveCharacterFromFavsUseCase,
    getFavouriteCharactersUseCase: GetFavouriteCharactersUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow(ListScreenState.makeInitialState())
    val screenState: StateFlow<ListScreenState> = combine(
        _screenState,
        getFavouriteCharactersUseCase.invoke()
    ){ uiState, favCharactersId ->
        uiState.copy(data = uiState.data.map {
            it.copy(isFavourite = favCharactersId.contains(it.id))
        })
    }.onStart {
        if (_screenState.value.showLoadingScreen) {
            getNextCharactersPage()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ListScreenState.makeInitialState()
    )

    private var nextPageJob: Job? = null

    fun applyGender(genderApplied: GenderDisplay) {
        _screenState.update { oldValue ->
            ListScreenState(
                filterSelected = genderApplied.takeIf { oldValue.filterSelected != it },
                data = emptyList(),
                paginationState = PaginationState.LOADING
            )
        }
        getNextCharactersPage()
    }

    fun getNextCharactersPage() {
        val uiState = screenState.value
        if (
            uiState.showLoadingScreen ||
            uiState.paginationState == PaginationState.ERROR ||
            uiState.paginationState == PaginationState.IDLE
        ) {
            nextPageJob?.cancel()
            nextPageJob = viewModelScope.launch {
                updateUiToLoadingNextPage()
                println("#### uiState == screenState.value: ${uiState == screenState.value}")
                println("#### param: ${uiState.filterSelected?.toDomain()}")
                getCharactersUseCase(uiState.filterSelected?.toDomain()).fold(
                    onSuccess = { result -> updateList(result) },
                    onFailure = { manageErrorResponse() },
                )
            }
        }
    }

    fun onClickFav(display: CharacterListItemDisplay) {
        if (display.isFavourite) {
            removeCharacterFromFavsUseCase(characterId = display.id)
        } else {
            addCharacterToFavsUseCase(characterId = display.id)
        }
    }

    private fun updateUiToLoadingNextPage() {
        _screenState.update { oldState ->
            oldState.copy(paginationState = PaginationState.LOADING)
        }
    }

    private fun updateList(result: CharacterPage) {
        _screenState.update { oldState ->
            val paginationState = if (result.isLastPage) {
                PaginationState.END
            } else {
                PaginationState.IDLE
            }
            val newData = result.data.toPresentation()

            val data = if (result.isFirstPage) { newData } else { oldState.data + newData }

            oldState.copy(
                paginationState = paginationState,
                data = data
            )
        }
    }

    private fun manageErrorResponse() {
        _screenState.update { oldState ->
            oldState.copy(paginationState = PaginationState.ERROR)
        }
    }
}