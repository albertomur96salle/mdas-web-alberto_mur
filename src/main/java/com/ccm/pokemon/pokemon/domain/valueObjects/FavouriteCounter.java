package com.ccm.pokemon.pokemon.domain.valueObjects;

public class FavouriteCounter {
    public FavouriteCounter(int value) {
        this.favouriteCounter = value;
    }

    private int favouriteCounter;

    public int getFavouriteCounter() {
        return this.favouriteCounter;
    }
}
