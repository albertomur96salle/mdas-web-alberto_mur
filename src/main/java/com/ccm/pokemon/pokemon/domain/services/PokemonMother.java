package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.user.user.domain.aggregate.User;
import com.ccm.user.user.domain.vo.UserId;

public class PokemonMother {
    public static Pokemon random() {
        return new Pokemon(
            PokemonIdMother.random(),
            PokemonNameMother.random()
        );
    }
}
