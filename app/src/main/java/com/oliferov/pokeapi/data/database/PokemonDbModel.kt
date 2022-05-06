package com.oliferov.pokeapi.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
class PokemonDbModel (
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String
)