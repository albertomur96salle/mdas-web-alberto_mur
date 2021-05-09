package com.ccm.user.user.domain.events;

import java.util.Objects;

public abstract class Event {
    public String exchange;
    public String routingKey;
    public String content;

    public Event(String exchange, String routingKey, String content) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.content = content;
    }

    public String getExchange() {
        return exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return exchange.equals(event.exchange) && routingKey.equals(event.routingKey) && content.equals(event.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchange, routingKey, content);
    }
}
