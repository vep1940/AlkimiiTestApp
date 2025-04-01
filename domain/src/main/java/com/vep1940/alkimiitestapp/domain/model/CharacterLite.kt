package com.vep1940.alkimiitestapp.domain.model

data class CharacterLite(
    val id: Int,
    val name: String,
    val gender: Gender,
    val origin: String?,
    val imageUrl: String?,
)
