package com.ccm.integration;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.application.useCases.AddFavouriteCountUseCase;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.infrastructure.eventlisteners.NewFavouritePokemonListener;
import com.ccm.user.user.domain.events.Event;
import com.ccm.user.user.domain.events.NewFavouritePokemonEvent;
import com.ccm.user.user.domain.interfaces.EventManager;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SendAndReceiveEventTest {
    @Inject
    @Named("RabbitMQ")
    EventManager eventManager;
    @Inject
    NewFavouritePokemonListener listener;

    PokemonDto pokemonDto;
    AddFavouriteCountUseCase useCase;

    @BeforeAll
    public void setup() throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        pokemonDto = new PokemonDto(1);
        useCase = Mockito.mock(AddFavouriteCountUseCase.class);
        Mockito.doNothing().when(useCase).addFavouriteCount(any());
        QuarkusMock.installMockForType(useCase, AddFavouriteCountUseCase.class);
    }

    @Test
    public void shouldSendAndReceiveEvent () throws IOException, PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException, InterruptedException {
        Event event = new NewFavouritePokemonEvent("pokemon", "newFavourite", "1");
        eventManager.publish(event);
        Thread.sleep(1000);

        Mockito.verify(useCase, Mockito.times(1)).addFavouriteCount(pokemonDto);
    }
}
