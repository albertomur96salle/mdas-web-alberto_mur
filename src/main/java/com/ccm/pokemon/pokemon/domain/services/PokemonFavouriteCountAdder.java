package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.interfaces.PokemonRepository;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemon.infrastructure.externalclients.PokemonApiClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class PokemonFavouriteCountAdder {
    @Inject
    @Named("MySQL")
    PokemonRepository pokemonRepository;

    /**
     * Uses the MySQL implementation of the PokemonRepository interface to retrieve a Pokemon from the repository and
     * increments its favourite counter before (by 1) persisting the Pokemon again
     *
     * @param pokemonId represents the Pokemon whose favourite counter is going to be incremented by 1
     * @throws PokemonNotFoundException
     * @throws TimeoutException
     * @throws UnknownException
     * @throws NetworkConnectionException
     */
    public void execute(PokemonId pokemonId) throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Pokemon pokemon = pokemonRepository.find(pokemonId);
        pokemon.incrementCounter();
        pokemonRepository.save(pokemon);
    }
}
