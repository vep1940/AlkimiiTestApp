package com.vep1940.alkimiitestapp.domain.model

data class CharacterPage(
    val data: List<CharacterLite>,
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
)
