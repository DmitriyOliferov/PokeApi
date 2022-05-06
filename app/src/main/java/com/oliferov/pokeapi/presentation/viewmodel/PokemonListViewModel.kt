package com.oliferov.pokeapi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oliferov.pokeapi.data.network.ApiFactory
import com.oliferov.pokeapi.data.paging.PokemonPageSource
import com.oliferov.pokeapi.data.paging.PokemonPagingSource
import com.oliferov.pokeapi.domain.Pokemon
import kotlinx.coroutines.flow.Flow

class PokemonListViewModel: ViewModel() {
    private val apiService = ApiFactory.apiService

//    val listData = Pager(PagingConfig(pageSize = 1,initialLoadSize = 1)){
//        PokemonPageSource(apiService)
//    }.flow.cachedIn(viewModelScope)

    fun getListData(): Flow<PagingData<Pokemon>> {
        return Pager(config = PagingConfig(pageSize = 60)){
            PokemonPagingSource(apiService)
        }.flow.cachedIn(viewModelScope)
    }
    suspend fun getPokemonList(offset: Int, limit: Int) = apiService.getPokemonList(offset, limit)
}