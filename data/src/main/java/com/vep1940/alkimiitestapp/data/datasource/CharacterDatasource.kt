package com.vep1940.alkimiitestapp.data.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.vep1940.alkimiitestapp.data.graphql.GetCharacterQuery
import com.vep1940.alkimiitestapp.data.graphql.GetCharactersQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CharacterDatasource(
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getCharacters(page: Int) = withContext(ioDispatcher) {
        apolloClient.query(GetCharactersQuery(page = Optional.present(page))).execute().data
    }

    suspend fun getCharacter(id: Int) = withContext(ioDispatcher) {
        apolloClient.query(GetCharacterQuery(id = id.toString())).execute().data
    }
}