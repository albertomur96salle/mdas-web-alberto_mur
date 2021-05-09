package com.ccm.pokemon.pokemon.infrastructure.parsers;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.valueObjects.Name;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonType;
import io.quarkus.test.junit.QuarkusTest;
import org.json.simple.JSONArray;
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
        Pokemon pokemon = new Pokemon(
            new PokemonId(1),
            new Name("bulbasaur")
        );
        pokemon.addPokemonType(new PokemonType("grass"));
        pokemon.addPokemonType(new PokemonType("poison"));

        JSONObject jsonPokemon = new JSONObject();
        jsonPokemon.put("id", ((Integer) 1).longValue());
        jsonPokemon.put("name", "bulbasaur");
        jsonPokemon.put("types", "grass, poison");
        jsonPokemon.put("favouriteCounter", 0);

        Assertions.assertEquals(pokemonToJsonParser.parse(pokemon), jsonPokemon.toJSONString());
    }
}
