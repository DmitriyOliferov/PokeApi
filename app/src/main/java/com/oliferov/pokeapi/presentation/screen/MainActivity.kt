package com.oliferov.pokeapi.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.oliferov.pokeapi.R
import com.oliferov.pokeapi.databinding.ActivityMainBinding
import com.oliferov.pokeapi.presentation.adapter.DefaultLoadStateAdapter
import com.oliferov.pokeapi.presentation.adapter.PokemonListAdapter
import com.oliferov.pokeapi.presentation.viewmodel.PokemonViewModelTest
import kotlinx.coroutines.flow.collectLatest


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launchPokemonListFragment()
    }

    private fun launchPokemonListFragment() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .addToBackStack("PokemonListFragment")
            .replace(binding.root.id, PokemonListFragment.getInstance())
            .commit()
    }


}