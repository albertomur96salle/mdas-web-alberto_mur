package com.ccm.user.user.domain.services;

import com.ccm.user.user.domain.events.Event;
import com.ccm.user.user.domain.events.NewFavouritePokemonEvent;
import com.ccm.user.user.domain.interfaces.EventManager;
import com.ccm.user.user.infrastructure.eventsenders.RabbitMqEventManager;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.io.IOException;

@QuarkusTest
public class EventPublisherTest {
    @Inject
    EventPublisher eventPublisher;

    @Test
    public void verify_publish_callsToMethods() throws IOException {
        Event event = Mockito.mock(NewFavouritePokemonEvent.class);
        EventManager eventManager = Mockito.mock(RabbitMqEventManager.class);

        Mockito.doNothing().when(eventManager).publish(event);
        QuarkusMock.installMockForType(eventManager, EventManager.class);

        eventPublisher.publish(event);

        Mockito.verify(eventManager, Mockito.times(1)).publish(event);
    }

    @Test
    public void verify_publish_throwsIOException () throws IOException {
        Event event = Mockito.mock(NewFavouritePokemonEvent.class);
        EventManager eventManager = Mockito.mock(RabbitMqEventManager.class);

        Mockito.doThrow(IOException.class).when(eventManager).publish(event);
        QuarkusMock.installMockForType(eventManager, EventManager.class);

        Assertions.assertThrows(IOException.class, () -> {eventPublisher.publish(event);});
    }
}
