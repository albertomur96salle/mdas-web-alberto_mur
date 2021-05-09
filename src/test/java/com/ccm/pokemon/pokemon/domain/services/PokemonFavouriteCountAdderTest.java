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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class PokemonFavouriteCountAdderTest {
    @Inject
    PokemonFavouriteCountAdder pokemonFavouriteCountAdder;

    @Test
    public void shouldIncrementFavouriteCount() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Pokemon pokemon = Mockito.mock(Pokemon.class);

        PokemonRepository pokemonRepository = Mockito.mock(MySqlPokemonRepository.class);
        Mockito.when(pokemonRepository.find(any())).thenReturn(pokemon);
        pokemon.incrementCounter();
        Mockito.doNothing().when(pokemonRepository).save(pokemon);
        QuarkusMock.installMockForType(pokemonRepository, PokemonRepository.class);

        pokemonFavouriteCountAdder.execute(new PokemonId(1));

        Mockito.verify(pokemonRepository, Mockito.times(1)).find(any());
        Mockito.verify(pokemonRepository, Mockito.times(1)).save(pokemon);
    }

    @Test
    public void shouldThrowPokemonNotFoundException() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Pokemon pokemon = Mockito.mock(Pokemon.class);
        PokemonId pokemonId = Mockito.mock(PokemonId.class);

        PokemonRepository pokemonRepository = Mockito.mock(MySqlPokemonRepository.class);
        Mockito.when(pokemonRepository.find(pokemonId)).thenThrow(PokemonNotFoundException.class);
        pokemon.incrementCounter();
        Mockito.doNothing().when(pokemonRepository).save(pokemon);
        QuarkusMock.installMockForType(pokemonRepository, PokemonRepository.class);

        assertThrows(PokemonNotFoundException.class, () -> {pokemonFavouriteCountAdder.execute(pokemonId);});
        Mockito.verify(pokemonRepository, Mockito.times(0)).save(pokemon);
    }
}
