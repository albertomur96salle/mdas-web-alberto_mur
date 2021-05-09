package com.ccm.pokemon.pokemon.infrastructure.eventlisteners;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.application.useCases.AddFavouriteCountUseCase;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.quarkus.runtime.Startup;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Startup
@ApplicationScoped
public class NewFavouritePokemonListener {
    @Inject
    AddFavouriteCountUseCase addFavouriteCountUseCase;
    private Logger logger = Logger.getLogger(NewFavouritePokemonListener.class.getName());

    @PostConstruct
    public void setUp() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.0.0.3");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                try {
                    logger.log(Level.INFO, "Someone added the Pokémon with ID " + message + " as favourite");
                    addFavouriteCountUseCase.addFavouriteCount(new PokemonDto(Integer.parseInt(message)));
                } catch (PokemonNotFoundException | com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException |
                        NetworkConnectionException | UnknownException e) {
                    e.printStackTrace();
                    logger.log(Level.WARNING, "There was a problem reading the incomming RabbitMQ message");
                }
            };

            channel.basicConsume("newFavourites", true, deliverCallback, consumerTag -> { });
            logger.log(Level.INFO, "Event listener setUp successfully executed");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
