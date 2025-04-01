package com.vep1940.alkimiitestapp.presentation.screen.list.ui

import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay
import com.vep1940.alkimiitestapp.presentation.screen.list.model.CharacterListItemDisplay
import org.jetbrains.annotations.TestOnly

@TestOnly
internal fun makeCharacterListItemsDisplay(size: Int = 10): List<CharacterListItemDisplay> {
    val itemList = mutableListOf<CharacterListItemDisplay>()
    repeat(size) { index ->
        itemList.add(makeCharacterListItemDisplay(index = index))
    }
    return itemList
}

@TestOnly
internal fun makeCharacterListItemDisplay(index: Int = 1) = CharacterListItemDisplay(
    id = index,
    imageUrl = when(index){
        1 -> null
        2 -> ""
        else -> "https://rickandmortyapi.com/api/character/avatar/$index.jpeg"
    },
    name = "Name$index",
    origin = "Origin$index",
    gender = GenderDisplay.entries[index % GenderDisplay.entries.size],
    isFavourite = false,
)