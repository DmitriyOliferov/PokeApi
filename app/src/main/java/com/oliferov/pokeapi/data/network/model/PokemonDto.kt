package com.oliferov.pokeapi.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonDto (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("sprites")
    @Expose
    val image: SpritesDto
)