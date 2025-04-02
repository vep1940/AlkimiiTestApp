package com.vep1940.alkimiitestapp.presentation.screen.detail.model

import com.vep1940.alkimiitestapp.domain.model.Character
import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay
import com.vep1940.alkimiitestapp.presentation.model.StatusDisplay
import com.vep1940.alkimiitestapp.presentation.model.toPresentation

internal data class CharacterDetailDisplay(
    val name: String,
    val status: StatusDisplay,
    val species: String?,
    val subspecies: String?,
    val gender: GenderDisplay,
    val origin: String?,
    val location: String?,
    val imageUrl: String?,
    val isFavourite: Boolean,
)

internal fun Character.toPresentation() = CharacterDetailDisplay(
    name = name,
    status = status.toPresentation(),
    species = species,
    subspecies = subspecies,
    gender = gender.toPresentation(),
    origin = origin,
    location = location,
    imageUrl = imageUrl,
    isFavourite = false,
)