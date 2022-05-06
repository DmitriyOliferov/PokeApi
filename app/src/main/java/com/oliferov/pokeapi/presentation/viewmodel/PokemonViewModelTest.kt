package com.oliferov.pokeapi.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oliferov.pokeapi.data.repository.RepositoryPokemonImpl
import com.oliferov.pokeapi.domain.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PokemonViewModelTest(
): ViewModel() {
    private val repository: RepositoryPokemonImpl

    val pokemonFlow: Flow<PagingData<Pokemon>>

    val pokemon = MutableLiveData<Pokemon>()

    init {
        repository = RepositoryPokemonImpl()
        pokemonFlow = repository.getPagedPokemons().cachedIn(viewModelScope)
    }

     fun getPokemon(name: String){
         viewModelScope.launch {
             pokemon.postValue(repository.getPokemon(name))
         }
    }
}