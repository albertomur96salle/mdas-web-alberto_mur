package com.ccm.pokemon.pokemon.application.dto;

import java.util.Objects;

public class PokemonDto {
    private int pokemonId;

    public int getPokemonId() {
        return pokemonId;
    }

    public PokemonDto(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonDto that = (PokemonDto) o;
        return pokemonId == that.pokemonId;
    }
}
