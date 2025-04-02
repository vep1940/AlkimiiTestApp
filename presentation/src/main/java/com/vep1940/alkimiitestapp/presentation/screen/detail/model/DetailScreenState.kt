package com.vep1940.alkimiitestapp.presentation.screen.detail.model

internal data class DetailScreenState(
    val data: CharacterDetailDisplay?,
    val dataStatus: DataStatus,
){
    companion object {
        fun makeInitialState() = DetailScreenState(
            data = null,
            dataStatus = DataStatus.LOADING,
        )
    }
}