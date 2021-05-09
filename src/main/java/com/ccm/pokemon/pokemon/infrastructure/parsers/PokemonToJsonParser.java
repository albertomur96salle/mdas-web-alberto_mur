package com.ccm.pokemon.pokemon.infrastructure.parsers;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import org.json.simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class PokemonToJsonParser {
    public String parse(Pokemon pokemon) {
        JSONObject resultPokemon = new JSONObject();

        List<String> typesList = new ArrayList<>();

        pokemon.getPokemonTypes().getPokemonTypes().forEach(type -> {
            typesList.add(type.getPokemonType());
        });

        String pokemonTypes = Arrays.toString(typesList.toArray())
            .replace("[", "").replace("]", "");

        resultPokemon.put("types", pokemonTypes);
        resultPokemon.put("name", pokemon.getName().getName());
        resultPokemon.put("id", pokemon.getPokemonId().getPokemonId());
        resultPokemon.put("favouriteCounter", pokemon.getFavouriteCounter().getFavouriteCounter());

        return resultPokemon.toJSONString();
    }
}
