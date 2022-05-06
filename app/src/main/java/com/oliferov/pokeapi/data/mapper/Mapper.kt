package com.oliferov.pokeapi.data.mapper

import com.oliferov.pokeapi.data.database.PokemonDbModel
import com.oliferov.pokeapi.data.network.model.PokemonDto
import com.oliferov.pokeapi.domain.Pokemon

object Mapper {

    fun mapPokemonDtoToPokemonDbModel(pokemonDto: PokemonDto) = PokemonDbModel(
        pokemonDto.id,
        pokemonDto.name,
        pokemonDto.image.imageUrl.imageUrl.imageUrl
    )

    fun mapPokemonDbModelToPokemon(pokemonDbModel: PokemonDbModel) = Pokemon(
        pokemonDbModel.id,
        pokemonDbModel.name,
        pokemonDbModel.imageUrl
    )

    fun mapPokemonDtoToPokemon(pokemonDto: PokemonDto) = Pokemon(
        pokemonDto.id,
        pokemonDto.name,
        pokemonDto.image.imageUrl.imageUrl.imageUrl
    )
}