package com.vep1940.alkimiitestapp.domain.usecase

import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository

class AddCharacterToFavsUseCase(
    private val characterRepository: CharacterRepository,
) {
    operator fun invoke(characterId: Int) = characterRepository.addToFavCharacters(characterId)
}