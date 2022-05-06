package com.oliferov.pokeapi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.oliferov.pokeapi.data.database.PokemonDbModel
import com.oliferov.pokeapi.data.network.model.PokemonDto
import com.oliferov.pokeapi.databinding.PokemonItemBinding
import com.oliferov.pokeapi.domain.Pokemon
import com.squareup.picasso.Picasso

class PokemonListAdapter: PagingDataAdapter<Pokemon, PokemonViewHolder>(PokemonDiffCallback()) {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentItem  = getItem(position) ?: return
        with(holder.binding){
            tvName.text = currentItem?.name
            Picasso.get().load(currentItem?.imageUrl).into(ivPokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }
}