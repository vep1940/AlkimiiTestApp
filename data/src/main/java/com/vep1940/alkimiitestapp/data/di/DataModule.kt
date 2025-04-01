package com.vep1940.alkimiitestapp.data.di

import com.apollographql.apollo.ApolloClient
import com.vep1940.alkimiitestapp.data.datasource.CharacterDatasource
import com.vep1940.alkimiitestapp.data.graphql.ApolloClientConfig
import com.vep1940.alkimiitestapp.data.repository.CharacterRepositoryImpl
import com.vep1940.alkimiitestapp.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    single<CoroutineDispatcher> { Dispatchers.IO }

    single<ApolloClient> { ApolloClientConfig.getApolloClient() }

    singleOf(::CharacterDatasource)

    factory<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }
}