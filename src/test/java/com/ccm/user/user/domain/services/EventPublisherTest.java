package com.ccm.user.user.domain.services;

import com.ccm.user.user.domain.events.Event;
import com.ccm.user.user.domain.events.NewFavouritePokemonEvent;
import com.ccm.user.user.domain.interfaces.EventManager;
import com.ccm.user.user.infrastructure.eventsenders.RabbitMqEventManager;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.io.IOException;

@QuarkusTest
public class EventPublisherTest {
    @Inject
    EventPublisher eventPublisher;

    static Event event;
    static EventManager eventManager;

    @BeforeAll
    public static void setUp() {
        event = Mockito.mock(NewFavouritePokemonEvent.class);
    }

    @BeforeEach
    public void setMocks() {
        eventManager = Mockito.mock(RabbitMqEventManager.class);
        QuarkusMock.installMockForType(eventManager, EventManager.class);
    }

    @Test
    public void verify_publish_callsToMethods() throws IOException {
        Mockito.doNothing().when(eventManager).publish(event);

        eventPublisher.publish(event);

        Mockito.verify(eventManager, Mockito.times(1)).publish(event);
    }

    @Test
    public void verify_publish_throwsIOException () throws IOException {
        Mockito.doThrow(IOException.class).when(eventManager).publish(event);

        Assertions.assertThrows(IOException.class, () -> {eventPublisher.publish(event);});
    }
}
