package com.vep1940.alkimiitestapp.presentation.screen.list.model

internal sealed class ListScreenState {
    data object Loading: ListScreenState()
    data object Error: ListScreenState()
    data class Idle(
        val value: List<CharacterListItemDisplay>,
    ): ListScreenState()
}