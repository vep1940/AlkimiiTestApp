package com.vep1940.alkimiitestapp.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String?,
    val subspecies: String?,
    val gender: Gender,
    val origin: String?,
    val location: String?,
    val imageUrl: String?,
)