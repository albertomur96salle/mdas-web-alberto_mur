package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.github.javafaker.Faker;

public class PokemonIdMother {
    public static PokemonId random() {
        return new PokemonId(new Faker().number().numberBetween(1, 898));
    }
}
