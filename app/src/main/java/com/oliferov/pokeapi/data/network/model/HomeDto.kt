package com.oliferov.pokeapi.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HomeDto(
    @Expose
    @SerializedName("front_default")
    val imageUrl: String
)