package com.vep1940.alkimiitestapp.data.model

import com.vep1940.alkimiitestapp.domain.model.CharacterPage

internal data class CharacterPageDto(
    val data: List<CharacterLiteDto>,
    val nextPage: Int?,
)

internal fun CharacterPageDto.toDomain(isFirstPage: Boolean) = CharacterPage(
    data = data.map { it.toDomain() },
    isFirstPage = isFirstPage,
    isLastPage = nextPage == null
)