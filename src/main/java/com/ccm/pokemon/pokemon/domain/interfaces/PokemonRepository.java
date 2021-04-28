package com.ccm.pokemon.pokemon.domain.interfaces;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemonTypes.domain.exceptions.PokemonNotFoundException;
import org.json.simple.JSONObject;

public interface PokemonRepository {

    public Pokemon find(PokemonId pokemonId) throws PokemonNotFoundException, TimeoutException, NetworkConnectionException, UnknownException;
}