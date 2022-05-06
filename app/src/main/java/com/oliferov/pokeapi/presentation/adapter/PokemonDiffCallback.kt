package com.oliferov.pokeapi.presentation.adapter

import androidx.paging.DifferCallback
import androidx.recyclerview.widget.DiffUtil
import com.oliferov.pokeapi.data.network.model.PokemonDto
import com.oliferov.pokeapi.domain.Pokemon

class PokemonDiffCallback: DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}