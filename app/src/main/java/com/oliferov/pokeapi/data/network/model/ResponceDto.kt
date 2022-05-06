package com.oliferov.pokeapi.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponceDto(
    @Expose
    @SerializedName("next")
    val next: String,
    @Expose
    @SerializedName("previous")
    val previous: String,
    @Expose
    @SerializedName("count")
    val count: Int,
    @Expose
    @SerializedName("results")
    val listPokemon: List<PokemonInListDto>
)