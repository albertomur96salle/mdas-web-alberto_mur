package com.ccm.pokemon.pokemon.domain.valueObjects;

import java.util.Objects;

public class FavouriteCounter {
    public FavouriteCounter(int value) {
        this.favouriteCounter = value;
    }

    private int favouriteCounter;

    public int getFavouriteCounter() {
        return this.favouriteCounter;
    }

    public void incrementCounter() {
        this.favouriteCounter++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteCounter that = (FavouriteCounter) o;
        return favouriteCounter == that.favouriteCounter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(favouriteCounter);
    }
}
