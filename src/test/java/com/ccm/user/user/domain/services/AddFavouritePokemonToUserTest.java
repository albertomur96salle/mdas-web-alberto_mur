package com.ccm.user.user.domain.services;

import com.ccm.user.user.domain.aggregate.User;
import com.ccm.user.user.domain.entities.FavouritePokemon;
import com.ccm.user.user.domain.exceptions.FavouritePokemonAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserNotFoundException;
import com.ccm.user.user.domain.vo.FavouritePokemonId;
import com.ccm.user.user.domain.vo.UserId;
import com.ccm.user.user.domain.vo.UserName;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
public class AddFavouritePokemonToUserTest {
    @Inject
    AddFavouritePokemonToUser tested;

    static UserId userId;
    static UserName userName;
    static User user;
    static FavouritePokemonId favouritePokemonId;
    static UserFinder userFinder;
    static UserSaver userSaver;

    @BeforeAll
    public static void setUp() {
        userId = new UserId(1);
        userName = new UserName("keko");
        user = Mockito.mock(User.class);
        favouritePokemonId = new FavouritePokemonId(123);
    }

    @BeforeEach
    public void setMocks() {
        userFinder = Mockito.mock(UserFinder.class);
        userSaver = Mockito.mock(UserSaver.class);
        QuarkusMock.installMockForType(userFinder, UserFinder.class);
        QuarkusMock.installMockForType(userSaver, UserSaver.class);
    }

    @Test
    public void verify_execute_callsToMethods() throws UserNotFoundException, FavouritePokemonAlreadyExistsException {
        when(userFinder.findUser(userId)).thenReturn(user);
        when(userSaver.saveUser(user)).thenReturn(user);

        tested.execute(favouritePokemonId, userId);
        Mockito.verify(userFinder, Mockito.times(1)).findUser(userId);
        Mockito.verify(userSaver, Mockito.times(1)).saveUser(user);
    }

    @Test
    public void verify_execute_throwsUserNotFoundException() throws UserNotFoundException {
        when(userFinder.findUser(userId)).thenThrow(UserNotFoundException.class);
        when(userSaver.saveUser(user)).thenReturn(user);

        assertThrows(UserNotFoundException.class, () -> {
            tested.execute(favouritePokemonId, userId);
        });
    }

    @Test
    public void verify_execute_throwsFavouritePokemonAlreadyExistsException() throws UserNotFoundException, FavouritePokemonAlreadyExistsException {
        FavouritePokemon favouritePokemon = new FavouritePokemon(favouritePokemonId);

        doThrow(FavouritePokemonAlreadyExistsException.class).when(user).addFavouritePokemon(favouritePokemon);
        when(userFinder.findUser(userId)).thenReturn(user);
        when(userSaver.saveUser(user)).thenReturn(user);

        assertThrows(FavouritePokemonAlreadyExistsException.class, () -> {
            tested.execute(favouritePokemonId, userId);
        });
    }
}
