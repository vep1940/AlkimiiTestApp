package com.vep1940.alkimiitestapp.domain.usecase

import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteCharactersUseCase(
    private val characterRepository: CharacterRepository,
) {
    operator fun invoke(): Flow<List<Int>> = characterRepository.getFavCharacters()
}