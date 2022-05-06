package com.oliferov.pokeapi.data.network

import com.oliferov.pokeapi.data.network.model.PokemonDto
import com.oliferov.pokeapi.data.network.model.ResponceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(END_POINT_LIST)
    suspend fun getPokemonList(
        @Query(OFFSET) offset: Int,
        @Query(LIMIT) limit: Int
    ): Response<ResponceDto>

    @GET(END_POINT_ONE)
    suspend fun getPokemon(
        @Path("name")name: String
    ): Response<PokemonDto>

    companion object{
        private const val OFFSET = "offset"
        private const val LIMIT = "limit"
        private const val END_POINT_LIST = "pokemon"
        private const val END_POINT_ONE = "pokemon/{name}"

    }
}