package com.vep1940.alkimiitestapp.data.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.vep1940.alkimiitestapp.data.graphql.GetCharacterQuery
import com.vep1940.alkimiitestapp.data.graphql.GetCharactersQuery
import com.vep1940.alkimiitestapp.data.graphql.type.FilterCharacter
import com.vep1940.alkimiitestapp.data.model.CharacterDto
import com.vep1940.alkimiitestapp.data.model.CharacterLiteDto
import com.vep1940.alkimiitestapp.data.model.CharacterPageDto
import com.vep1940.alkimiitestapp.data.model.GenderDto
import com.vep1940.alkimiitestapp.data.model.toGenderDto
import com.vep1940.alkimiitestapp.data.model.toStatusDto
import com.vep1940.alkimiitestapp.data.model.toStringValue
import com.vep1940.alkimiitestapp.lang.multiLet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class CharacterNetworkDatasource(
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun getCharacters(page: Int, filter: GenderDto?) = withContext(ioDispatcher) {
        apolloClient.query(
            GetCharactersQuery(
                page = Optional.present(page),
                filter = Optional.presentIfNotNull(filter?.toGraphQl()),
            )
        ).execute().data?.toDto()
    }

    suspend fun getCharacter(id: Int) = withContext(ioDispatcher) {
        apolloClient.query(GetCharacterQuery(id = id.toString())).execute().data?.toDto()
    }

    private fun GenderDto.toGraphQl() = FilterCharacter(
        name = Optional.Absent,
        status = Optional.Absent,
        species = Optional.Absent,
        type = Optional.Absent,
        gender = Optional.present(this.toStringValue())
    )

    private fun GetCharactersQuery.Data.toDto(): CharacterPageDto? =
        characters?.results?.mapNotNull { it?.toDto() }?.let { dataList ->
            CharacterPageDto(
                data = dataList,
                nextPage = characters.info?.next,
            )
        }

    private fun GetCharactersQuery.Result.toDto() = multiLet(id, name) { (id, name) ->
        id.toIntOrNull()?.let { idInt ->
            CharacterLiteDto(
                id = idInt,
                name = name,
                gender = gender.toGenderDto(),
                origin = origin?.name,
                imageUrl = image,
            )
        }
    }

    private fun GetCharacterQuery.Data.toDto(): CharacterDto? = character?.toDto()

    private fun GetCharacterQuery.Character.toDto(): CharacterDto? = multiLet(id, name) { (id, name) ->
        id.toIntOrNull()?.let { idInt ->
            CharacterDto(
                id = idInt,
                name = name,
                status = status.toStatusDto(),
                species = species,
                subspecies = type,
                gender = gender.toGenderDto(),
                origin = origin?.name,
                location = location?.name,
                imageUrl = image,
            )
        }
    }
}