package com.ccm.pokemon.pokemon.infrastructure.repositories;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.interfaces.PokemonRepository;
import com.ccm.pokemon.pokemon.domain.valueObjects.Name;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonType;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.infrastructure.externalclients.PokemonApiClient;
import com.ccm.pokemon.pokemon.infrastructure.parsers.JsonToPokemonParser;
import io.quarkus.test.junit.QuarkusTest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.inject.Named;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class MySQLPokemonRepositoryTest {
    @Inject
    @Named("MySQL")
    PokemonRepository pokemonRepository;

    @Inject
    PokemonApiClient pokemonApiClient;

    @Inject
    JsonToPokemonParser jsonToPokemonParser;

    @Test
    public void shouldFindPokemon() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        //Given
        PokemonId pokemonId = new PokemonId(1);
        Name pokemonName = new Name("bulbasaur");
        Pokemon pokemon = new Pokemon(pokemonId, pokemonName);
        pokemon.addPokemonType(new PokemonType("grass"));
        pokemon.addPokemonType(new PokemonType("poison"));

        //When
        Pokemon retrievedPokemon = pokemonRepository.find(pokemonId);

        //Then
        Assertions.assertEquals(pokemon, retrievedPokemon);
    }

    @Test
    public void shouldThrowPokemonNotFoundException() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        //Given
        PokemonId pokemonId = new PokemonId(0);

        //When ("then" stage is also included because of the exception)
        assertThrows(
            com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException.class,
            () -> {
                pokemonRepository.find(pokemonId);
            }
        );
    }

    @Test
    public void shouldSaveAndFind() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        //Given
        JSONObject toBeSavedPokemonJson = pokemonApiClient.find(new PokemonId(1));
        Pokemon toBeSavedPokemon = jsonToPokemonParser.castJsonToPokemon(toBeSavedPokemonJson);

        //When
        pokemonRepository.save(toBeSavedPokemon);

        //Then
        assertEquals(pokemonRepository.find(toBeSavedPokemon.getPokemonId()), toBeSavedPokemon);
        pokemonRepository.delete(toBeSavedPokemon.getPokemonId());
    }
}
