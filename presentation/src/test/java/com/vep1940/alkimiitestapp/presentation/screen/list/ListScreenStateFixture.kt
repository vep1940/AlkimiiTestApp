package com.vep1940.alkimiitestapp.presentation.screen.list

import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay
import com.vep1940.alkimiitestapp.presentation.screen.list.model.CharacterListItemDisplay
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenState
import com.vep1940.alkimiitestapp.presentation.screen.list.model.PaginationState

internal object ListScreenStateFixture {

    fun givenListScreenState(
        filterSelected: GenderDisplay? = null,
        data: List<CharacterListItemDisplay> = givenCharacterListItemDisplays(),
        paginationState: PaginationState = PaginationState.IDLE,
    ) = ListScreenState(
        filterSelected = filterSelected,
        data = data,
        paginationState = paginationState
    )

    fun givenCharacterListItemDisplays(
        size: Int = 3,
        offset: Int = 0,
        favItemIndexes: List<Int> = listOf(),
    ): List<CharacterListItemDisplay> {
        val list = mutableListOf<CharacterListItemDisplay>()
        repeat(size) { index ->
            val realIndex = index + offset
            val characterListItemDisplay = givenCharacterListItemDisplay(
                id = realIndex,
                name = "name$realIndex",
                isFavourite = favItemIndexes.contains(realIndex)
            )
            list.add(characterListItemDisplay)
        }
        return list
    }

    fun givenCharacterListItemDisplay(
        id: Int = 1,
        imageUrl: String? = "imageUrl",
        name: String = "name",
        origin: String? = "origin",
        gender: GenderDisplay = GenderDisplay.FEMALE,
        isFavourite: Boolean = false,
    ) = CharacterListItemDisplay(
        id = id,
        imageUrl = imageUrl,
        name = name,
        origin = origin,
        gender = gender,
        isFavourite = isFavourite
    )
}