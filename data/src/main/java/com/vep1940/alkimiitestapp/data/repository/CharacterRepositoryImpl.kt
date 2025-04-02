package com.vep1940.alkimiitestapp.data.repository

import com.vep1940.alkimiitestapp.data.datasource.CharacterLocalDatasource
import com.vep1940.alkimiitestapp.data.datasource.CharacterNetworkDatasource
import com.vep1940.alkimiitestapp.data.datasource.FavouriteLocalDatasource
import com.vep1940.alkimiitestapp.data.model.toDomain
import com.vep1940.alkimiitestapp.data.model.toDto
import com.vep1940.alkimiitestapp.domain.model.Character
import com.vep1940.alkimiitestapp.domain.model.CharacterPage
import com.vep1940.alkimiitestapp.domain.model.ErrorType
import com.vep1940.alkimiitestapp.domain.model.Gender
import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class CharacterRepositoryImpl(
    private val characterLocalDatasource: CharacterLocalDatasource,
    private val characterNetworkDatasource: CharacterNetworkDatasource,
    private val favouriteLocalDatasource: FavouriteLocalDatasource,
    private val ioDispatcher: CoroutineDispatcher,
) : CharacterRepository {

    companion object {
        const val FIRST_PAGE = 1
    }

    private var charactersNextPage: Int = FIRST_PAGE
    private var lastGenderFiltered: Gender? = null

    override suspend fun getCharacters(gender: Gender?): Result<CharacterPage> =
        withContext(ioDispatcher) {
            try {
                val page = if (lastGenderFiltered == gender) charactersNextPage else FIRST_PAGE
                val filter = gender?.toDto()

                val data = characterLocalDatasource.getPage(pageIndex = page, filter = filter)
                    ?: characterNetworkDatasource.getCharacters(page = page, filter = filter)

                val result = data?.toDomain(isFirstPage = page == FIRST_PAGE)

                if (result == null || result.data.isEmpty()) {
                    Result.failure(ErrorType.NoData)
                } else {
                    Result.success(result).also {
                        characterLocalDatasource.insertPage(
                            pageIndex = page,
                            filter = filter,
                            data = data,
                        )
                        updateRepositoryPagingStatus(gender, data.nextPage)
                    }
                }
            } catch (t: Throwable) {
                Result.failure(ErrorType.Server)
            }
        }

    private fun updateRepositoryPagingStatus(
        lastGender: Gender?,
        nextPage: Int?,
    ) {
        lastGenderFiltered = lastGender
        nextPage?.let { charactersNextPage = it }
    }

    override suspend fun getCharacter(id: Int): Result<Character> = withContext(ioDispatcher) {
        try {
            val data = characterLocalDatasource.getCharacter(id)
                ?: characterNetworkDatasource.getCharacter(id)

            data?.toDomain()?.let { result ->
                Result.success(result).also {
                    characterLocalDatasource.insertCharacter(id = id, data = data)
                }
            } ?: Result.failure(ErrorType.NoData)
        } catch (t: Throwable) {
            Result.failure(ErrorType.Server)
        }
    }

    override fun getFavCharacters(): Flow<List<Int>> =
        favouriteLocalDatasource.getAll().map { query ->
            query.executeAsList().map { id -> id.toInt() }
        }

    override fun getFavCharacterStatus(id: Int): Flow<Boolean> =
        favouriteLocalDatasource.get(id).map { query ->
            query.executeAsOneOrNull() != null
        }

    override fun addToFavCharacters(charactedId: Int) {
        favouriteLocalDatasource.insertFav(charactedId)
    }

    override fun removeFromFavCharacters(charactedId: Int) {
        favouriteLocalDatasource.removeFav(charactedId)
    }
}