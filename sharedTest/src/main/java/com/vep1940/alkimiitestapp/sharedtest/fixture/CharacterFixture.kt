package com.vep1940.alkimiitestapp.sharedtest.fixture

import com.vep1940.alkimiitestapp.domain.model.Character
import com.vep1940.alkimiitestapp.domain.model.CharacterLite
import com.vep1940.alkimiitestapp.domain.model.CharacterPage
import com.vep1940.alkimiitestapp.domain.model.Gender
import com.vep1940.alkimiitestapp.domain.model.Status

object CharacterFixture {

    fun givenCharacterPage(
        data: List<CharacterLite> = givenCharacterLiteList(),
        isFirstPage: Boolean = false,
        isLastPage: Boolean = false,
    ) = CharacterPage(
        data = data,
        isFirstPage = isFirstPage,
        isLastPage = isLastPage,
    )

    fun givenCharacterLiteList(
        size: Int = 3,
        offset: Int = 0,
    ): List<CharacterLite> {
        val list = mutableListOf<CharacterLite>()
        repeat(size) { index ->
            val realIndex = index + offset
            val characterLite = givenCharacterLite(
                id = realIndex,
                name = "name$realIndex",
            )
            list.add(characterLite)
        }
        return list
    }

    fun givenCharacterLite(
        id: Int = 1,
        name: String = "name",
        gender: Gender = Gender.FEMALE,
        origin: String? = "origin",
        imageUrl: String? = "imageUrl",
    ) = CharacterLite(
        id = id,
        name = name,
        gender = gender,
        origin = origin,
        imageUrl = imageUrl,
    )

    fun givenCharacter(
        id: Int = 1,
        name: String = "name",
        status: Status = Status.ALIVE,
        species: String? = "species",
        subspecies: String? = "subspecies",
        gender: Gender = Gender.FEMALE,
        origin: String? = "origin",
        location: String? = "location",
        imageUrl: String? = "imageUrl",
    ) = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        subspecies = subspecies,
        gender = gender,
        origin = origin,
        location = location,
        imageUrl = imageUrl,
    )
}