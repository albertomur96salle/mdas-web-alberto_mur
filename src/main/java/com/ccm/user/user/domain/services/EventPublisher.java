package com.ccm.user.user.domain.services;

import com.ccm.user.user.domain.events.Event;
import com.ccm.user.user.domain.interfaces.EventManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@ApplicationScoped
public class EventPublisher {

    @Inject
    @Named("RabbitMQ")
    EventManager eventManager;

    public void publish(Event event) throws IOException {
        eventManager.publish(event);
    }
}
