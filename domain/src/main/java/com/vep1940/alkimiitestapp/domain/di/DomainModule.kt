package com.vep1940.alkimiitestapp.domain.di

import com.vep1940.alkimiitestapp.domain.usecase.AddCharacterToFavsUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetCharacterUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetCharactersUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetFavouriteCharacterUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetFavouriteCharactersUseCase
import com.vep1940.alkimiitestapp.domain.usecase.RemoveCharacterFromFavsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetCharactersUseCase)
    factoryOf(::GetCharacterUseCase)
    singleOf(::AddCharacterToFavsUseCase)
    singleOf(::RemoveCharacterFromFavsUseCase)
    singleOf(::GetFavouriteCharactersUseCase)
    singleOf(::GetFavouriteCharacterUseCase)
}