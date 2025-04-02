package com.vep1940.alkimiitestapp.data.datasource

import com.vep1940.alkimiitestapp.data.model.CharacterDto
import com.vep1940.alkimiitestapp.data.model.CharacterPageDto
import com.vep1940.alkimiitestapp.data.model.CharacterPageParamsDto
import com.vep1940.alkimiitestapp.data.model.GenderDto

internal class CharacterLocalDatasource {

    private val pages = mutableMapOf<CharacterPageParamsDto, CharacterPageDto>()
    private val details = mutableMapOf<Int, CharacterDto>()

    fun insertPage(
        pageIndex: Int,
        filter: GenderDto?,
        data: CharacterPageDto,
    ) {
        pages[CharacterPageParamsDto(pageIndex, filter)] = data
    }

    fun getPage(pageIndex: Int, filter: GenderDto?) = pages[CharacterPageParamsDto(pageIndex, filter)]

    fun insertCharacter(
        id: Int,
        data: CharacterDto,
    ) {
        details[id] = data
    }

    fun getCharacter(id: Int) = details[id]
}