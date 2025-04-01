package com.vep1940.alkimiitestapp.domain.usecase

import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository

class GetCharacterUseCase(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(id: Int) = characterRepository.getCharacter(id)
}