package com.vep1940.alkimiitestapp.di

import com.vep1940.alkimiitestapp.data.di.dataModule
import com.vep1940.alkimiitestapp.domain.di.domainModule
import com.vep1940.alkimiitestapp.presentation.di.presentationModule
import org.koin.dsl.module

val appModule = module {
    includes(presentationModule)
    includes(domainModule)
    includes(dataModule)
}