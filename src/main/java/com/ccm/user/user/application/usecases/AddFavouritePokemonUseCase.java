package com.ccm.user.user.application.usecases;

import com.ccm.user.user.application.dto.UserFavouritePokemonDTO;
import com.ccm.user.user.domain.events.Event;
import com.ccm.user.user.domain.events.NewFavouritePokemonEvent;
import com.ccm.user.user.domain.exceptions.FavouritePokemonAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserNotFoundException;
import com.ccm.user.user.domain.services.AddFavouritePokemonToUser;
import com.ccm.user.user.domain.services.EventPublisher;
import com.ccm.user.user.domain.vo.FavouritePokemonId;
import com.ccm.user.user.domain.vo.UserId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class AddFavouritePokemonUseCase {

    @Inject
    AddFavouritePokemonToUser addFavouritePokemonToUser;

    @Inject
    EventPublisher eventPublisher;

    public void addFavouritePokemon (UserFavouritePokemonDTO user) throws UserNotFoundException, FavouritePokemonAlreadyExistsException, IOException {
        FavouritePokemonId pokemonId = new FavouritePokemonId(user.getPokemonId());
        UserId userId = new UserId(user.getUserId());

        addFavouritePokemonToUser.execute(pokemonId, userId);

        Event newFavouritePokemonEvent = new NewFavouritePokemonEvent(
                "pokemon",
                "newFavourite",
                String.valueOf(pokemonId.getPokemonId())
        );
        eventPublisher.publish(newFavouritePokemonEvent);
    }
}
