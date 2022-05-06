package com.oliferov.pokeapi.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.oliferov.pokeapi.R
import com.oliferov.pokeapi.databinding.FragmentPokemonListBinding
import com.oliferov.pokeapi.databinding.PartDefaultLoadStateBinding
import com.oliferov.pokeapi.domain.Pokemon
import com.oliferov.pokeapi.presentation.adapter.DefaultLoadStateAdapter
import com.oliferov.pokeapi.presentation.adapter.PokemonListAdapter
import com.oliferov.pokeapi.presentation.viewmodel.PokemonViewModelTest
import kotlinx.coroutines.flow.collectLatest

class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding
        get() = _binding ?: throw RuntimeException("PokemonListFragment is null")


    private lateinit var pokemonListAdapter: PokemonListAdapter

    private lateinit var viewModel: PokemonViewModelTest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PokemonViewModelTest::class.java]

        setupRv()
        loadingData()
    }

    companion object{
        fun getInstance(): PokemonListFragment {
            return PokemonListFragment()
        }
    }

    private fun loadingData() {
        lifecycleScope.launchWhenCreated {
            viewModel.pokemonFlow.collectLatest {
                pokemonListAdapter.submitData(it)
            }
        }
    }

    private fun setupRv() {
        pokemonListAdapter = PokemonListAdapter()
        pokemonListAdapter.onPokemonClickListener = object : PokemonListAdapter.OnPokemonClickListener{
            override fun onPokemonClick(pokemon: Pokemon) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .addToBackStack("PokemonDetailFragment")
                    .replace(R.id.fragment_container,PokemonDetailFragment.getInstance(pokemon.name))
                    .commit()
            }

        }
        val pokemonListAdapterWithLoadState =
            pokemonListAdapter
                .withLoadStateFooter(DefaultLoadStateAdapter { pokemonListAdapter.retry() })

        binding.rvPokemonList.apply {
            adapter = pokemonListAdapterWithLoadState
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        pokemonListAdapter.addLoadStateListener { state: CombinedLoadStates ->
            val refreshState = state.refresh
            binding.rvPokemonList.isVisible = refreshState != LoadState.Loading
            binding.progressBar.isVisible = refreshState == LoadState.Loading
            if (refreshState is LoadState.Error) {
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