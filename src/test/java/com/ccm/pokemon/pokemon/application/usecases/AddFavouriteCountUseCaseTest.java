package com.ccm.pokemon.pokemon.application.usecases;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.application.useCases.AddFavouriteCountUseCase;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.services.PokemonFavouriteCountAdder;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.user.user.domain.exceptions.FavouritePokemonAlreadyExistsException;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class AddFavouriteCountUseCaseTest {
    @Inject
    AddFavouriteCountUseCase addFavouriteCountUseCase;

    static PokemonFavouriteCountAdder pokemonFavouriteCountAdder;
    static PokemonDto pokemonDto;
    static PokemonId pokemonId;

    @BeforeAll
    public static void setUp() {
        pokemonDto = new PokemonDto(1);
        pokemonId = new PokemonId(pokemonDto.getPokemonId());
    }

    @BeforeEach
    public void setMocks() {
        pokemonFavouriteCountAdder = Mockito.mock(PokemonFavouriteCountAdder.class);
        QuarkusMock.installMockForType(pokemonFavouriteCountAdder, PokemonFavouriteCountAdder.class);
    }

    @Test
    public void shouldIncrementFavouriteCount() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Mockito.doNothing().when(pokemonFavouriteCountAdder).execute(pokemonId);

        addFavouriteCountUseCase.addFavouriteCount(pokemonDto);

        Mockito.verify(pokemonFavouriteCountAdder, Mockito.times(1)).execute(pokemonId);
    }

    @Test
    public void shouldThrowPokemonNotFound() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Mockito.doThrow(PokemonNotFoundException.class).when(pokemonFavouriteCountAdder).execute(pokemonId);

        assertThrows(PokemonNotFoundException.class, () -> {
            addFavouriteCountUseCase.addFavouriteCount(pokemonDto);
        });
    }
}
