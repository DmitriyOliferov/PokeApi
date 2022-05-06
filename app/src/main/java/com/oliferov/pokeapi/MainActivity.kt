package com.oliferov.pokeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.oliferov.pokeapi.databinding.ActivityMainBinding
import com.oliferov.pokeapi.databinding.PokemonItemBinding
import com.oliferov.pokeapi.presentation.adapter.PokemonListAdapter
import com.oliferov.pokeapi.presentation.viewmodel.PokemonListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private val viewModel: PokemonListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRv()
        loadingData()
    }

    private fun loadingData() {

        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                pokemonListAdapter.submitData(it)
            }
        }
    }

    private fun setupRv() {
        pokemonListAdapter = PokemonListAdapter()
        binding.rvPokemonList.apply {
            adapter = pokemonListAdapter
            layoutManager = StaggeredGridLayoutManager(
                2,StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL))
        }
        pokemonListAdapter.addLoadStateListener { state:CombinedLoadStates ->
            val refreshState = state.refresh
            binding.rvPokemonList.isVisible = refreshState != LoadState.Loading
            binding.progressBar.isVisible = refreshState == LoadState.Loading
            if(refreshState is LoadState.Error) {
                Snackbar.make(
                    binding.root,
                    refreshState.error.localizedMessage ?: "",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }

        }
    }
}