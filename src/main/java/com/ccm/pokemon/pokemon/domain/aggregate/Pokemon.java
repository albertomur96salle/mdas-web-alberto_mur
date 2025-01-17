package com.ccm.pokemon.pokemon.domain.aggregate;

import com.ccm.pokemon.pokemon.domain.valueObjects.*;

import java.util.Objects;

public class Pokemon {
    private Name name;
    private PokemonId pokemonId;
    private PokemonTypes pokemonTypes;
    private FavouriteCounter favouriteCounter;

    public Pokemon(PokemonId pokemonId, Name name) {
        this.name = name;
        this.pokemonId = pokemonId;
        this.pokemonTypes = new PokemonTypes();
        this.favouriteCounter = new FavouriteCounter(0);
    }

    public Name getName() {
        return name;
    }

    public PokemonId getPokemonId() {
        return pokemonId;
    }

    public PokemonTypes getPokemonTypes() {
        return pokemonTypes;
    }

    public FavouriteCounter getFavouriteCounter() {
        return favouriteCounter;
    }

    public void addPokemonType(PokemonType pokemonType) {
        this.pokemonTypes.addType(pokemonType);
    }

    public void incrementCounter() {
        this.favouriteCounter.incrementCounter();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return name.equals(pokemon.name) && pokemonId.equals(pokemon.pokemonId) && pokemonTypes.equals(pokemon.pokemonTypes) && favouriteCounter.equals(pokemon.favouriteCounter);
    }
}
