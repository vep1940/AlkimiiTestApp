package com.vep1940.alkimiitestapp.domain.usecase

import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCharactersUseCase(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke() = characterRepository.getCharacters()
}