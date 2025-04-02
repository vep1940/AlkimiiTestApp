package com.vep1940.alkimiitestapp.data.model

import com.vep1940.alkimiitestapp.domain.model.Gender

internal enum class GenderDto {
    FEMALE,
    MALE,
    GENDERLESS,
    UNKNOWN,
}

internal fun GenderDto.toDomain() = when (this) {
    GenderDto.FEMALE -> Gender.FEMALE
    GenderDto.MALE -> Gender.MALE
    GenderDto.GENDERLESS -> Gender.GENDERLESS
    GenderDto.UNKNOWN -> Gender.UNKNOWN
}

internal fun Gender.toDto() = when (this) {
    Gender.FEMALE -> GenderDto.FEMALE
    Gender.MALE -> GenderDto.MALE
    Gender.GENDERLESS -> GenderDto.GENDERLESS
    Gender.UNKNOWN -> GenderDto.UNKNOWN
}

internal fun String?.toGenderDto() = when (this) {
    "Female" -> GenderDto.FEMALE
    "Male" -> GenderDto.MALE
    "Genderless" -> GenderDto.GENDERLESS
    else -> GenderDto.UNKNOWN
}

internal fun GenderDto.toStringValue() = when (this) {
    GenderDto.FEMALE -> "Female"
    GenderDto.MALE -> "Male"
    GenderDto.GENDERLESS -> "Genderless"
    GenderDto.UNKNOWN -> "Unknown"
}

