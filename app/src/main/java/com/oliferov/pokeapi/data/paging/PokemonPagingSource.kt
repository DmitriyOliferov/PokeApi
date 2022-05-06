package com.oliferov.pokeapi.data.paging

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oliferov.pokeapi.data.mapper.Mapper
import com.oliferov.pokeapi.data.network.ApiService
import com.oliferov.pokeapi.data.network.model.PokemonDto
import com.oliferov.pokeapi.domain.Pokemon
import java.lang.Exception

class PokemonPagingSource(private val apiService: ApiService) : PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getPokemonList(nextPage, LIMIT_CHARACTERS)
            var nextPageNumber: Int? = null
            if (response.body()?.next != null) {
                val uri = Uri.parse(response.body()?.next)
                val nextPageQuery = uri.getQueryParameter(OFFSET)
                nextPageNumber = nextPageQuery?.toInt()
            }
            var prevPageNumber: Int? = null
            if (response.body()?.previous != null) {
                val uri = Uri.parse(response.body()?.previous)
                val prevPageQuery = uri.getQueryParameter(OFFSET)
                nextPageNumber = prevPageQuery?.toInt()
            }
            val listPokemon = response.body()?.listPokemon?.map {
                apiService.getPokemon(it.name)
            }?.mapNotNull {
                it.body()
            }?.map {
                Mapper.mapPokemonDtoToPokemon(it)
            } ?: emptyList()
            Log.d("DxD","$listPokemon")
            LoadResult.Page(
                data = listPokemon,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
        private const val LIMIT_CHARACTERS = 60
        private const val OFFSET = "offset"
        private const val LIMIT = "limit"
    }
}