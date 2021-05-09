package com.ccm.pokemon.pokemon.infrastructure.databaseentities;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="pokemon")
public class PokemonDB {
    @Id
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="types", nullable = false)
    private String types;

    @Column(name="counter", nullable = false)
    private int counter;

    public PokemonDB() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
