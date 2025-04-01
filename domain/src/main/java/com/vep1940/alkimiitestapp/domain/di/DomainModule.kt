package com.vep1940.alkimiitestapp.domain.di

import com.vep1940.alkimiitestapp.domain.usecase.GetCharacterUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetCharactersUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetCharactersUseCase)
    factoryOf(::GetCharacterUseCase)
}