package com.vep1940.alkimiitestapp.domain.usecase

import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteCharacterUseCase(
    private val characterRepository: CharacterRepository,
) {
    operator fun invoke(id: Int): Flow<Boolean> = characterRepository.getFavCharacterStatus(id = id)
}