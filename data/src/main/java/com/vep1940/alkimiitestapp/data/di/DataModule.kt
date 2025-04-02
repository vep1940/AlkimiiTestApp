package com.vep1940.alkimiitestapp.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.apollographql.apollo.ApolloClient
import com.vep1940.alkimiitestapp.FavCharacterDatabase
import com.vep1940.alkimiitestapp.data.datasource.CharacterLocalDatasource
import com.vep1940.alkimiitestapp.data.datasource.CharacterNetworkDatasource
import com.vep1940.alkimiitestapp.data.datasource.FavouriteLocalDatasource
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

    single<FavCharacterDatabase> { FavCharacterDatabase(get()) }
    single<SqlDriver> { AndroidSqliteDriver(FavCharacterDatabase.Schema, get(), "favouriteCharacter.db")}

    singleOf(::CharacterLocalDatasource)
    singleOf(::CharacterNetworkDatasource)
    singleOf(::FavouriteLocalDatasource)

    factory<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get(), get()) }
}