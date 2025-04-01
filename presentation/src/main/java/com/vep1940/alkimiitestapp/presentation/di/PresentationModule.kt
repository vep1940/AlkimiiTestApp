package com.vep1940.alkimiitestapp.presentation.di

import com.vep1940.alkimiitestapp.presentation.screen.detail.DetailViewModel
import com.vep1940.alkimiitestapp.presentation.screen.list.ListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::ListViewModel)
    viewModelOf(::DetailViewModel)
}