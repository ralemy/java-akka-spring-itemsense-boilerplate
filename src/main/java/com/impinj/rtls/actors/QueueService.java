package com.impinj.rtls.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impinj.rtls.itemsense.*;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import javax.inject.Named;
import java.io.IOException;

/**
 * Created by ralemy on 8/8/16.
 * stub service to decide what to do with a received message.
 */

@Named("QueueService")
public class QueueService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Connection amqp;
    private Channel channel;

    public void process(QueueMessage msg) {
        log.info(msg.toString());
    }

    public void connectToItmeSense(Api itemsense, String user, String pass) throws IOException {
        log.info("connecting to itemsene");
        QueueResponse response = Queue.register(itemsense, new QueueRequest());
        amqp = openAmqpConnection(response.getServerUrl(), user, pass);
        channel = amqp.createChannel(true);
        channel.basicConsume(response.getQueue(), true, new amqpListener(channel));
        log.info("connected to Itemsense");
    }

    private Connection openAmqpConnection(String url, String user, String pass) {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setUri(url);
        factory.setUsername(user);
        factory.setPassword(pass);


        return factory.createConnection();
    }

    public void stopQueue() {
        log.info("stopping AMQP connection");
        try {
            if (channel != null)
                channel.close();
            if (amqp != null)
                amqp.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error stopping AMQP listener " + e.getMessage());
        }
    }

    private class amqpListener extends DefaultConsumer {

        private ObjectMapper mapper = new ObjectMapper();

        public amqpListener(Channel ch) {
            super(ch);
        }

        @Override
        public void handleDelivery(
                String consumerTag,
                Envelope envelope,
                AMQP.BasicProperties properties,
                byte[] body) throws IOException {
            log.info("message received");
            try {
                QueueMessage msg = mapper.readValue(body, QueueMessage.class);
                process(msg);
            } catch (Exception e) {
                log.info("Exception in message:" + e.getMessage());
            }
        }
    }

}
