package com.oliferov.pokeapi.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oliferov.pokeapi.data.database.PokemonDbModel
import com.oliferov.pokeapi.data.mapper.Mapper
import com.oliferov.pokeapi.data.network.ApiService
import com.oliferov.pokeapi.domain.Pokemon
import retrofit2.HttpException

class PokemonPageSource(
    private val service: ApiService
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(60) ?: page.nextKey?.minus(60)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val page: Int = params.key ?: 0
        var pageSize = params.loadSize

        val response = service.getPokemonList(page, pageSize)
        if (response.isSuccessful) {
            pageSize = (response.body()?.count ?: 1)
            val result = mutableListOf<Pokemon>()
            val listDto = checkNotNull(response.body()).listPokemon.map {
                service.getPokemon(it.name).body()
            }.map {
                it?.let {
                    result.add(Mapper.mapPokemonDbModelToPokemon(Mapper.mapPokemonDtoToPokemonDbModel(it)))
                }
            }
            val nextKey = if(result.size < pageSize) null else page + pageSize
            val prevKey = if(page == 0) null else page - pageSize
            Log.d("DxD","$result")
            return LoadResult.Page(result,prevKey,nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }
}