package com.vep1940.alkimiitestapp.presentation.screen.detail.model

internal sealed class DetailScreenState {
    data object Loading: DetailScreenState()
    data object Error: DetailScreenState()
    data class Idle(val value: CharacterDetailDisplay): DetailScreenState()
}