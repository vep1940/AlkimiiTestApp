package com.vep1940.alkimiitestapp.data.model

import com.vep1940.alkimiitestapp.domain.model.Status

enum class StatusDto {
    ALIVE,
    DEAD,
    UNKNOWN,
}

internal fun StatusDto.toDomain() = when (this) {
    StatusDto.ALIVE -> Status.ALIVE
    StatusDto.DEAD -> Status.DEAD
    StatusDto.UNKNOWN -> Status.UNKNOWN
}

internal fun String?.toStatusDto() = when (this) {
    "Alive" -> StatusDto.ALIVE
    "Dead" -> StatusDto.DEAD
    else -> StatusDto.UNKNOWN
}