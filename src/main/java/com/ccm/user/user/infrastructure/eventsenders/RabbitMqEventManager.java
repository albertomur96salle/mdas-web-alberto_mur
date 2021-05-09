package com.ccm.user.user.infrastructure.eventsenders;

import com.ccm.user.user.domain.events.Event;
import com.ccm.user.user.domain.interfaces.EventManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
@Named("RabbitMQ")
public class RabbitMqEventManager implements EventManager {
    private Channel channel;

    public RabbitMqEventManager() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.0.0.3");
        Connection connection = null;
        try {
            connection = factory.newConnection();
            this.channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(Event event) throws IOException {
        channel.basicPublish(
            event.exchange,
            event.getRoutingKey(),
            null,
            event.getContent().getBytes(StandardCharsets.UTF_8)
        );
    }
}
