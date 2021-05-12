package com.ccm.pokemon.pokemon.infrastructure.parsers;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.services.PokemonMother;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonType;
import io.quarkus.test.junit.QuarkusTest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class PokemonToJsonParserTest {
    @Inject
    PokemonToJsonParser pokemonToJsonParser;

    @Test
    public void shouldParsePokemontoPokemon() {
        Pokemon pokemon = PokemonMother.random();
        pokemon.addPokemonType(new PokemonType("grass"));
        pokemon.addPokemonType(new PokemonType("poison"));

        JSONObject jsonPokemon = new JSONObject();
        jsonPokemon.put("id", ((Integer) pokemon.getPokemonId().getPokemonId()).longValue());
        jsonPokemon.put("name", pokemon.getName().getName());
        jsonPokemon.put("types", "grass, poison");
        jsonPokemon.put("favouriteCounter", 0);

        Assertions.assertEquals(pokemonToJsonParser.parse(pokemon), jsonPokemon.toJSONString());
    }
}
