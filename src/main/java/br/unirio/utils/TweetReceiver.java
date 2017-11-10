package br.unirio.utils;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TweetReceiver {
    private final static String QUEUE_NAME = "tweet";

    public static void receiveTweet()
            throws java.io.IOException,
            java.lang.InterruptedException, TimeoutException {
        final TweetTagger tagger = new TweetTagger("resources/model-experimento10.ser.gz");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                tagger.tagFromJSON(message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
