package com.vep1940.alkimiitestapp.presentation.screen.detail.ui

import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay
import com.vep1940.alkimiitestapp.presentation.model.StatusDisplay
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.CharacterDetailDisplay
import org.jetbrains.annotations.TestOnly

@TestOnly
internal fun makeCharacterDisplay() = CharacterDetailDisplay(
    name = "Rick Sánchez",
    status = StatusDisplay.ALIVE,
    species = "Human",
    subspecies = null,
    gender = GenderDisplay.MALE,
    origin = "Terra",
    location = "Terra",
    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
)