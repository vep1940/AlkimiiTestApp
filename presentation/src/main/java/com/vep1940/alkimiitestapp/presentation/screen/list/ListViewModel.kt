package com.vep1940.alkimiitestapp.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vep1940.alkimiitestapp.domain.usecase.GetCharactersUseCase
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenState
import com.vep1940.alkimiitestapp.presentation.screen.list.model.toPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ListScreenState>(ListScreenState.Loading)
    val screenState: StateFlow<ListScreenState> = _screenState.onStart {
        getCharactersPage()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ListScreenState.Loading
    )

    fun getCharactersPage() {
        viewModelScope.launch {
            getCharactersUseCase().fold(
                onSuccess = { data ->
                    _screenState.update { ListScreenState.Idle(data.toPresentation()) }
                },
                onFailure = {
                    _screenState.update { ListScreenState.Error }
                },
            )
        }
    }
}