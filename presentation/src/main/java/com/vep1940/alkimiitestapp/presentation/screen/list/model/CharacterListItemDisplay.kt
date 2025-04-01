package com.vep1940.alkimiitestapp.presentation.screen.list.model

import com.vep1940.alkimiitestapp.domain.model.CharacterLite
import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay
import com.vep1940.alkimiitestapp.presentation.model.toPresentation

internal data class CharacterListItemDisplay(
    val id: Int,
    val imageUrl: String?,
    val name: String,
    val origin: String?,
    val gender: GenderDisplay,
    val isFavourite: Boolean,
)

internal fun List<CharacterLite>.toPresentation() = map {
    CharacterListItemDisplay(
        id = it.id,
        imageUrl = it.imageUrl,
        name = it.name,
        origin = it.origin,
        gender = it.gender.toPresentation(),
        isFavourite = false,
    )
}