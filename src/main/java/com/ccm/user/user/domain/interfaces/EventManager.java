package com.ccm.user.user.domain.interfaces;

import com.ccm.user.user.domain.events.Event;

import java.io.IOException;

public interface EventManager {
    public void publish(Event event) throws IOException;
}
