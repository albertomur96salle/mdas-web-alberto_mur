package com.ccm.pokemon.pokemon.infrastructure.externalclients;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonType;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonTypes;
import com.ccm.pokemon.pokemon.infrastructure.parsers.JsonToPokemonParser;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class PokemonApiClientTest {
    @Inject
    PokemonApiClient pokemonApiClient;
    @Inject
    JsonToPokemonParser jsonToPokemonParser;

    @Test
    public void shouldFindPokemon() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        //Given
        PokemonId pokemonId = new PokemonId(1);
        PokemonTypes types = new PokemonTypes();
        types.addType(new PokemonType("grass"));
        types.addType(new PokemonType("poison"));

        //When
        Pokemon pokemon = jsonToPokemonParser.castJsonToPokemon(pokemonApiClient.find(pokemonId));

        //Then
        Assertions.assertEquals(pokemon.getPokemonId().getPokemonId(), pokemonId.getPokemonId());
        Assertions.assertEquals(pokemon.getName().getName(), "bulbasaur");
        Assertions.assertEquals(pokemon.getPokemonTypes(), types);
        Assertions.assertEquals(pokemon.getFavouriteCounter().getFavouriteCounter(), 0);
    }

    @Test
    public void shouldNotFindPokemon() {
        //Given
        PokemonId pokemonId = new PokemonId(0);

        //When+Then
        assertThrows(PokemonNotFoundException.class, () -> {pokemonApiClient.find(pokemonId);});
    }
}
