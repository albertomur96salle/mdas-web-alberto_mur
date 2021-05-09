package com.ccm.user.user.infrastructure.eventsenders;

import com.ccm.user.user.domain.events.Event;
import com.ccm.user.user.domain.events.NewFavouritePokemonEvent;
import com.ccm.user.user.domain.interfaces.EventManager;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.inject.Named;

@QuarkusTest
public class RabbitMqEventManagerTest {
    @Inject
    @Named("RabbitMQ")
    EventManager eventManager;

    private void setUp() {

    }

    @Test
    public void shouldPublishMessage() {
        Event event = new NewFavouritePokemonEvent("pokemon", "newFavourite", "1");

    }
}
