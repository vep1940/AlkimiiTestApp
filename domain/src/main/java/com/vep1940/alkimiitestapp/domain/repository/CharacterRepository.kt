package com.vep1940.alkimiitestapp.domain.repository

import com.vep1940.alkimiitestapp.domain.model.Character
import com.vep1940.alkimiitestapp.domain.model.CharacterLite

interface CharacterRepository {
    suspend fun getCharacters(): Result<List<CharacterLite>>
    suspend fun getCharacter(id: Int): Result<Character>
}