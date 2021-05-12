package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.pokemon.pokemon.domain.valueObjects.Name;
import com.github.javafaker.Faker;

public class PokemonNameMother {
    public static Name random() {
        return new Name(new Faker().pokemon().name());
    }
}
