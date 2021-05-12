package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.interfaces.PokemonRepository;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemon.infrastructure.repositories.MySqlPokemonRepository;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class PokemonFavouriteCountAdderTest {
    @Inject
    PokemonFavouriteCountAdder pokemonFavouriteCountAdder;

    static Pokemon pokemon;
    static PokemonRepository pokemonRepository;

    @BeforeEach
    public void setUp() {
        pokemon = PokemonMother.random();
        pokemonRepository = Mockito.mock(MySqlPokemonRepository.class);
        QuarkusMock.installMockForType(pokemonRepository, PokemonRepository.class);
    }

    @Test
    public void shouldIncrementFavouriteCount() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Mockito.when(pokemonRepository.find(any())).thenReturn(pokemon);
        pokemon.incrementCounter();
        Mockito.doNothing().when(pokemonRepository).save(pokemon);

        pokemonFavouriteCountAdder.execute(new PokemonId(1));

        Mockito.verify(pokemonRepository, Mockito.times(1)).find(any());
        Mockito.verify(pokemonRepository, Mockito.times(1)).save(pokemon);
    }

    @Test
    public void shouldThrowPokemonNotFoundException() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Mockito.when(pokemonRepository.find(pokemon.getPokemonId())).thenThrow(PokemonNotFoundException.class);

        assertThrows(PokemonNotFoundException.class, () -> {pokemonFavouriteCountAdder.execute(pokemon.getPokemonId());});
    }
}
