package com.oliferov.pokeapi.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpritesDto(
    @Expose
    @SerializedName("other")
    val imageUrl: OtherDto
)

