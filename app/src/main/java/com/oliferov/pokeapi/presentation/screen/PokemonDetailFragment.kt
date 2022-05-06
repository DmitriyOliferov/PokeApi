package com.oliferov.pokeapi.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.oliferov.pokeapi.databinding.FragmentPokemonDetailBinding
import com.oliferov.pokeapi.domain.Pokemon
import com.oliferov.pokeapi.presentation.viewmodel.PokemonViewModelTest
import com.squareup.picasso.Picasso
import java.lang.RuntimeException

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding: FragmentPokemonDetailBinding
        get() = _binding ?: throw RuntimeException("PokemonDetailFragment is null")

    private lateinit var viewModel: PokemonViewModelTest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(
            inflater, container, false
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
        val pokemonName = arguments?.getString(POKEMON_NAME) ?: ""
        Log.d("DxDF",pokemonName)
        viewModel.getPokemon(pokemonName)
        viewModel.pokemon.observe(viewLifecycleOwner) {
            with(binding) {
                Picasso.get().load(it.imageUrl).into(ivPokemon)
                tvId.text = it.id.toString()
                tvName.text = it.name
            }
        }
    }

    companion object {

        private const val POKEMON_NAME = "pokemon"

        fun getInstance(name: String): PokemonDetailFragment {
            return PokemonDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(POKEMON_NAME, name)
                }
            }
        }
    }
}