package com.ccm.pokemon.pokemon.application.useCases;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.services.PokemonFavouriteCountAdder;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AddFavouriteCountUseCase {

    @Inject
    PokemonFavouriteCountAdder pokemonFavouriteCountAdder;

    /**
     * Increments (by 1) the counter associated with the number of times the Pokemon "pokemon" has been
     * marked as a favourite (for the first time)
     *
     * @param pokemon contains a numeric identifier representing a Pokemon
     * @throws PokemonNotFoundException
     * @throws TimeoutException
     * @throws UnknownException
     * @throws NetworkConnectionException
     */
    public void addFavouriteCount(PokemonDto pokemon) throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        pokemonFavouriteCountAdder.execute(new PokemonId(pokemon.getPokemonId()));
    }
}
