package com.ccm.pokemon.pokemon.domain.interfaces;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;

public interface PokemonRepository {

    /**
     * Retrieves a Pokemon from the repository based on the identifier passed as parameter
     *
     * @param pokemonId is the identifier of a Pokemon
     * @return
     * @throws PokemonNotFoundException
     * @throws TimeoutException
     * @throws NetworkConnectionException
     * @throws UnknownException
     */
    public Pokemon find(PokemonId pokemonId) throws PokemonNotFoundException, TimeoutException, NetworkConnectionException, UnknownException;

    /**
     * Persists the chosen Pokemon in the repository
     *
     * @param pokemon represents the Pokemon that will be persisted
     */
    public void save(Pokemon pokemon);

    /**
     * Removes the Pokemon whose identifier equals to the one passed as parameter
     *
     * @param pokemonId represents the Pokemon to be removed from the repository
     */
    public void delete(PokemonId pokemonId);
}
