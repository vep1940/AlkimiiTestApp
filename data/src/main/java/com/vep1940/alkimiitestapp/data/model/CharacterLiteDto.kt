package com.vep1940.alkimiitestapp.data.model

import com.vep1940.alkimiitestapp.domain.model.CharacterLite

internal data class CharacterLiteDto(
    val id: Int,
    val name: String,
    val gender: GenderDto,
    val origin: String?,
    val imageUrl: String?,
)

internal fun CharacterLiteDto.toDomain() = CharacterLite(
    id = id,
    name = name,
    gender = gender.toDomain(),
    origin = origin,
    imageUrl = imageUrl
)