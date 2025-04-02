package com.vep1940.alkimiitestapp.data.model

import com.vep1940.alkimiitestapp.domain.model.Character

internal data class CharacterDto(
    val id: Int,
    val name: String,
    val status: StatusDto,
    val species: String?,
    val subspecies: String?,
    val gender: GenderDto,
    val origin: String?,
    val location: String?,
    val imageUrl: String?,
)

internal fun CharacterDto.toDomain() = Character(
    id = id,
    name = name,
    status = status.toDomain(),
    species = species,
    subspecies = subspecies,
    gender = gender.toDomain(),
    origin = origin,
    location = location,
    imageUrl = imageUrl
)