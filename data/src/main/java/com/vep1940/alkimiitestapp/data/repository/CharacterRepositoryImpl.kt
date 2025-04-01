package com.vep1940.alkimiitestapp.data.repository

import com.vep1940.alkimiitestapp.data.datasource.CharacterDatasource
import com.vep1940.alkimiitestapp.data.mapper.toDomain
import com.vep1940.alkimiitestapp.domain.model.Character
import com.vep1940.alkimiitestapp.domain.model.CharacterLite
import com.vep1940.alkimiitestapp.domain.model.ErrorType
import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CharacterRepositoryImpl(
    private val datasource: CharacterDatasource,
    private val ioDispatcher: CoroutineDispatcher,
): CharacterRepository {

    private var charactersNextPage: Int = 1

    override suspend fun getCharacters(): Result<List<CharacterLite>> = withContext(ioDispatcher) {
        try {
            val raw = datasource.getCharacters(charactersNextPage)
            raw?.characters?.info?.next?.let { nextPageIndex -> charactersNextPage = nextPageIndex }
            val data = raw?.toDomain()
            if (data.isNullOrEmpty()) {
                Result.failure(ErrorType.NoData)
            } else {
                Result.success(data)
            }
        } catch (t: Throwable) {
            Result.failure(ErrorType.Server)
        }
    }

    override suspend fun getCharacter(id: Int): Result<Character> = withContext(ioDispatcher) {
        try {
            datasource.getCharacter(id)?.toDomain()?.let { data ->
                Result.success(data)
            } ?: Result.failure(ErrorType.NoData)
        } catch (t: Throwable) {
            Result.failure(ErrorType.Server)
        }
    }
}