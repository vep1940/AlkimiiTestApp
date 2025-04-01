package com.vep1940.alkimiitestapp.data.mapper

import com.vep1940.alkimiitestapp.data.graphql.GetCharacterQuery
import com.vep1940.alkimiitestapp.data.graphql.GetCharactersQuery
import com.vep1940.alkimiitestapp.domain.model.Character
import com.vep1940.alkimiitestapp.domain.model.CharacterLite
import com.vep1940.alkimiitestapp.domain.model.Gender
import com.vep1940.alkimiitestapp.domain.model.Status
import com.vep1940.alkimiitestapp.lang.multiLet

internal fun GetCharactersQuery.Data.toDomain(): List<CharacterLite> =
    characters?.results?.mapNotNull { it?.toDomain() } ?: emptyList()

private fun GetCharactersQuery.Result.toDomain() = multiLet(id, name) { (id, name) ->
    id.toIntOrNull()?.let { idInt ->
        CharacterLite(
            id = idInt,
            name = name,
            gender = gender.toGender(),
            origin = origin?.name,
            imageUrl = image,
        )
    }
}

internal fun GetCharacterQuery.Data.toDomain(): Character? = character?.toDomain()

private fun GetCharacterQuery.Character.toDomain(): Character? = multiLet(id, name) { (id, name) ->
    id.toIntOrNull()?.let { idInt ->
        Character(
            id = idInt,
            name = name,
            status = status.toStatus(),
            species = species,
            subspecies = type,
            gender = gender.toGender(),
            origin = origin?.name,
            location = location?.name,
            imageUrl = image,
        )
    }
}

private fun String?.toGender() = when(this) {
    "Female" -> Gender.FEMALE
    "Male" -> Gender.MALE
    "Genderless" -> Gender.GENDERLESS
    else -> Gender.UNKNOWN
}

private fun String?.toStatus() = when(this) {
    "Alive" -> Status.ALIVE
    "Dead" -> Status.DEAD
    else -> Status.UNKNOWN
}