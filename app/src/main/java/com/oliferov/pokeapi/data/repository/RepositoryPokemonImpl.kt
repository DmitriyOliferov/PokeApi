package com.oliferov.pokeapi.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oliferov.pokeapi.data.mapper.Mapper
import com.oliferov.pokeapi.data.network.ApiFactory
import com.oliferov.pokeapi.data.paging.PokemonPageLoader
import com.oliferov.pokeapi.data.paging.PokemonTestPaginationSource
import com.oliferov.pokeapi.domain.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RepositoryPokemonImpl() {

    fun getPagedPokemons(): Flow<PagingData<Pokemon>> {
        val loader: PokemonPageLoader = { pageIndex: Int, pageSize: Int ->
            getPokemons(pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,

            ),
            pagingSourceFactory = {PokemonTestPaginationSource(loader, PAGE_SIZE)}
        ).flow
    }

    private suspend fun getPokemons(pageIndex: Int, pageSize: Int): List<Pokemon> =
        withContext(Dispatchers.IO) {
            val offset = pageIndex * pageSize
            val list = ApiFactory.apiService
                .getPokemonList(offset, pageSize)
                .body()
                ?.listPokemon
                ?.map {
                    ApiFactory.apiService.getPokemon(it.name)
                }?.mapNotNull {
                    it.body()
                }?.map {
                    Mapper.mapPokemonDtoToPokemon(it)
                } ?: emptyList()
            return@withContext list
        }

    suspend fun getPokemon(name: String): Pokemon{
        val pokemon = ApiFactory.apiService.getPokemon(name).body()
        Log.d("DxD",pokemon.toString())
        val result = Mapper.mapPokemonDtoToPokemon(pokemon!!)
        return result
    }

    companion object{
        const val PAGE_SIZE = 30
    }

}