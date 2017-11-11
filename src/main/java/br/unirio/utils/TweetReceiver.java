package br.unirio.utils;

import com.rabbitmq.client.*;

import br.unirio.models.ToolProperties;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.time.StopWatch;

public class TweetReceiver {
    private final static String QUEUE_NAME = ToolProperties.getInstance().getQueueName();

    public static void receiveTweet()
            throws java.io.IOException,
            java.lang.InterruptedException, TimeoutException {
        final TweetTagger tagger = new TweetTagger("resources/model-experimento10.ser.gz");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        final StopWatch sw = new StopWatch();
        if(ToolProperties.getInstance().getConsumerTime() > 0){ 
            sw.start();
            System.out.print("[*] Receiving Messages.");
        }
        else{
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        }

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                tagger.tagFromJSON(message);
                if(verifyStop(sw.getTime(TimeUnit.HOURS))){
                    this.getChannel().basicCancel(consumerTag);  
                }
            }
        };
        
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    private static boolean verifyStop(long hour){
        return (ToolProperties.getInstance().getConsumerTime() > 0 && hour >= ToolProperties.getInstance().getConsumerTime());
    }
}
