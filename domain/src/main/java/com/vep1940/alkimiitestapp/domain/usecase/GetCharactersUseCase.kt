package com.vep1940.alkimiitestapp.domain.usecase

import com.vep1940.alkimiitestapp.domain.model.Gender
import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository

class GetCharactersUseCase(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(gender: Gender?) = characterRepository.getCharacters(gender = gender)
}