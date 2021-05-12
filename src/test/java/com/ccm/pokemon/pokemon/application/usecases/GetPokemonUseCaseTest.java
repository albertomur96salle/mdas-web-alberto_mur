package com.ccm.pokemon.pokemon.application.usecases;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.application.useCases.AddFavouriteCountUseCase;
import com.ccm.pokemon.pokemon.application.useCases.GetPokemonUseCase;
import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.services.PokemonFavouriteCountAdder;
import com.ccm.pokemon.pokemon.domain.services.PokemonFinder;
import com.ccm.pokemon.pokemon.domain.valueObjects.Name;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class GetPokemonUseCaseTest {
    @Inject
    GetPokemonUseCase getPokemonUseCase;

    static PokemonFinder pokemonFinder;
    static PokemonDto pokemonDto;
    static PokemonId pokemonId;
    static Pokemon pokemon;

    @BeforeAll
    public static void setUp() {
        pokemonDto = new PokemonDto(1);
        pokemonId = new PokemonId(pokemonDto.getPokemonId());
        pokemon = new Pokemon(pokemonId, new Name("bulbasaur"));
    }

    @BeforeEach
    public void setMocks() {
        pokemonFinder = Mockito.mock(PokemonFinder.class);
        QuarkusMock.installMockForType(pokemonFinder, PokemonFinder.class);
    }

    @Test
    public void shouldIncrementFavouriteCount() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Mockito.when(pokemonFinder.findPokemon(pokemonId)).thenReturn(pokemon);

        getPokemonUseCase.getPokemonByPokemonId(pokemonDto);

        Mockito.verify(pokemonFinder, Mockito.times(1)).findPokemon(pokemonId);
    }

    @Test
    public void shouldThrowPokemonNotFound() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Mockito.doThrow(PokemonNotFoundException.class).when(pokemonFinder).findPokemon(pokemonId);

        assertThrows(PokemonNotFoundException.class, () -> {
            getPokemonUseCase.getPokemonByPokemonId(pokemonDto);
        });
    }
}
