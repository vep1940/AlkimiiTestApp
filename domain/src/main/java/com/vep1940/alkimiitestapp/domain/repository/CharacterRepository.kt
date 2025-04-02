package com.vep1940.alkimiitestapp.domain.repository

import com.vep1940.alkimiitestapp.domain.model.Character
import com.vep1940.alkimiitestapp.domain.model.CharacterPage
import com.vep1940.alkimiitestapp.domain.model.Gender
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(gender: Gender?): Result<CharacterPage>
    suspend fun getCharacter(id: Int): Result<Character>
    fun getFavCharacters(): Flow<List<Int>>
    fun getFavCharacterStatus(id: Int): Flow<Boolean>
    fun addToFavCharacters(charactedId: Int)
    fun removeFromFavCharacters(charactedId: Int)
}