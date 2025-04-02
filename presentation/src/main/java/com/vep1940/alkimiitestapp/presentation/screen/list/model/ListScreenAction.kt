package com.vep1940.alkimiitestapp.presentation.screen.list.model

import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay

internal data class ListScreenAction(
    val onClickItem: (id: Int) -> Unit,
    val onClickFilter: (genderFilter: GenderDisplay) -> Unit,
    val onClickFav: (character: CharacterListItemDisplay) -> Unit,
    val onEnd: () -> Unit,
)
