package com.ccm.user.user.application.usecases;

import com.ccm.user.user.application.dto.UserFavouritePokemonDTO;
import com.ccm.user.user.domain.events.Event;
import com.ccm.user.user.domain.events.NewFavouritePokemonEvent;
import com.ccm.user.user.domain.exceptions.FavouritePokemonAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserNotFoundException;
import com.ccm.user.user.domain.services.AddFavouritePokemonToUser;
import com.ccm.user.user.domain.services.EventPublisher;
import com.ccm.user.user.domain.vo.FavouritePokemonId;
import com.ccm.user.user.domain.vo.UserId;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@QuarkusTest
public class AddFavouritePokemonUseCaseTest {
    @Inject
    AddFavouritePokemonUseCase tested;

    static FavouritePokemonId pokemonId;
    static UserId userId;
    static UserFavouritePokemonDTO userFavouritePokemonDTO;
    static Event newFavouritePokemonEvent;
    static AddFavouritePokemonToUser addFavouritePokemonToUser;
    static EventPublisher eventPublisher;

    @BeforeAll
    public static void setUp() {
        pokemonId = new FavouritePokemonId(123);
        userId = new UserId(1);
        userFavouritePokemonDTO = new UserFavouritePokemonDTO(
                pokemonId.getPokemonId(),
                userId.getUserId()
        );
        newFavouritePokemonEvent = new NewFavouritePokemonEvent(
                "pokemon",
                "newFavourite",
                String.valueOf(pokemonId.getPokemonId())
        );
    }

    @BeforeEach
    public void setMocks() {
        addFavouritePokemonToUser = Mockito.mock(AddFavouritePokemonToUser.class);
        eventPublisher = Mockito.mock(EventPublisher.class);
        QuarkusMock.installMockForType(addFavouritePokemonToUser, AddFavouritePokemonToUser.class);
        QuarkusMock.installMockForType(eventPublisher, EventPublisher.class);
    }

    @Test
    public void verify_addFavouritePokemon_CallsToMethods() throws UserNotFoundException, FavouritePokemonAlreadyExistsException, IOException {
        Mockito.doNothing().when(addFavouritePokemonToUser).execute(pokemonId, userId);
        Mockito.doNothing().when(eventPublisher).publish(newFavouritePokemonEvent);

        tested.addFavouritePokemon(userFavouritePokemonDTO);

        Mockito.verify(addFavouritePokemonToUser, times(1)).execute(pokemonId, userId);
        Mockito.verify(eventPublisher, times(1)).publish(newFavouritePokemonEvent);
    }

    @Test
    public void verify_addFavouritePokemon_throwsUserNotFoundException () throws UserNotFoundException, FavouritePokemonAlreadyExistsException, IOException {
        Mockito.doThrow(UserNotFoundException.class).when(addFavouritePokemonToUser).execute(pokemonId, userId);
        Mockito.doNothing().when(eventPublisher).publish(newFavouritePokemonEvent);

        assertThrows(UserNotFoundException.class, () -> {
            tested.addFavouritePokemon(userFavouritePokemonDTO);
        });
        Mockito.verify(eventPublisher, times(0)).publish(newFavouritePokemonEvent);
    }

    @Test
    public void verify_addFavouritePokemon_throwsFavouritePokemonAlreadyExistsException () throws UserNotFoundException, FavouritePokemonAlreadyExistsException, IOException {
        Mockito.doThrow(FavouritePokemonAlreadyExistsException.class).when(addFavouritePokemonToUser).execute(pokemonId, userId);
        Mockito.doNothing().when(eventPublisher).publish(newFavouritePokemonEvent);

        assertThrows(FavouritePokemonAlreadyExistsException.class, () -> {
            tested.addFavouritePokemon(userFavouritePokemonDTO);
        });
        Mockito.verify(eventPublisher, times(0)).publish(newFavouritePokemonEvent);
    }

    @Test
    public void verify_addFavouritePokemon_throwsIOException () throws UserNotFoundException, FavouritePokemonAlreadyExistsException, IOException {
        Mockito.doNothing().when(addFavouritePokemonToUser).execute(pokemonId, userId);
        Mockito.doThrow(IOException.class).when(eventPublisher).publish(newFavouritePokemonEvent);

        assertThrows(IOException.class, () -> {
            tested.addFavouritePokemon(userFavouritePokemonDTO);
        });
        Mockito.verify(addFavouritePokemonToUser, times(1)).execute(pokemonId, userId);
    }
}
