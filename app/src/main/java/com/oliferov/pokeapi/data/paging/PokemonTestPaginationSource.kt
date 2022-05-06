package com.oliferov.pokeapi.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oliferov.pokeapi.domain.Pokemon


typealias PokemonPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Pokemon>

class PokemonTestPaginationSource(
    private val loader: PokemonPageLoader,
    private val pageSize: Int
) : PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val pageIndex = params.key ?: FIRST_OFFSET_INDEX

        return try{
            val pokemons = loader.invoke(pageIndex,params.loadSize)
            Log.d("DxD","$pokemons")
            return  LoadResult.Page(
                data = pokemons,
                prevKey = if(pageIndex == 0) null else pageIndex - 1,
                nextKey = if(pokemons.size == params.loadSize)pageIndex + (params.loadSize/pageSize) else null
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_OFFSET_INDEX = 0
        private const val LIMIT_CHARACTERS = 60
        private const val OFFSET = "offset"
        private const val LIMIT = "limit"
    }
}