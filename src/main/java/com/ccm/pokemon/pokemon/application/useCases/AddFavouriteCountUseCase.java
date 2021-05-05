package com.ccm.pokemon.pokemon.application.useCases;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.domain.services.PokemonFavouriteCountAdder;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;

import javax.inject.Inject;

public class AddFavouriteCountUseCase {

    @Inject
    PokemonFavouriteCountAdder pokemonFavouriteCountAdder;

    public void addFavouriteCount(PokemonDto pokemon) {
        pokemonFavouriteCountAdder.execute(new PokemonId(pokemon.getPokemonId()));
    }
}
