package com.vep1940.alkimiitestapp.presentation.screen.list.model

import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay

internal data class ListScreenState(
    val filterSelected: GenderDisplay?,
    val data: List<CharacterListItemDisplay>,
    val paginationState: PaginationState,
) {
    val showList = data.isNotEmpty()
    val showLoadingScreen = data.isEmpty() && paginationState == PaginationState.LOADING
    val showErrorScreen = data.isEmpty() && paginationState == PaginationState.ERROR
    val showErrorFooter = showList && paginationState == PaginationState.ERROR
    val showEndFooter = showList && paginationState == PaginationState.END

    companion object {
        fun makeInitialState() = ListScreenState(
            filterSelected = null,
            data = emptyList(),
            paginationState = PaginationState.LOADING,
        )
    }
}