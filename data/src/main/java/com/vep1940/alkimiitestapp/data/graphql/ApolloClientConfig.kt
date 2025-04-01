package com.vep1940.alkimiitestapp.data.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache

object ApolloClientConfig {

    fun getApolloClient() = ApolloClient
        .Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}