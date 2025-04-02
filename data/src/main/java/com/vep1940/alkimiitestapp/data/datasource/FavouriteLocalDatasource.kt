package com.vep1940.alkimiitestapp.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import com.vep1940.alkimiitestapp.FavCharacterDatabase

internal class FavouriteLocalDatasource(
    private val database: FavCharacterDatabase,
) {

    fun getAll() = database.favCharacterIdQueries.getAll().asFlow()

    fun get(id: Int) = database.favCharacterIdQueries.get(id.toLong()).asFlow()

    fun insertFav(id: Int) {
        database.favCharacterIdQueries.insert(id.toLong())
    }

    fun removeFav(id: Int) {
        database.favCharacterIdQueries.delete(id.toLong())
    }
}