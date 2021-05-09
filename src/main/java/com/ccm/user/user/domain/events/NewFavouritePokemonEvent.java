package com.ccm.user.user.domain.events;

public class NewFavouritePokemonEvent extends Event {
    public NewFavouritePokemonEvent(String exchange, String routingKey, String content) {
        super(exchange, routingKey, content);
    }
}
