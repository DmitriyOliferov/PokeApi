package com.oliferov.pokeapi.data.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon " +
//            "WHERE :searchBy = '' OR name LIKE '%'"
"ORDER BY id " +
            "LIMIT :limit OFFSET :offset"
    )
    suspend fun getPokemonList(
        limit: Int,
        offset: Int,
//        searchBy: String = ""
    ): List<PokemonDbModel>
}