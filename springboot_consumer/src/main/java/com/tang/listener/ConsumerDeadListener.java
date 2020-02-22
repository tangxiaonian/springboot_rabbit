package com.tang.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Classname ConsumerDeadListener
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/28 9:40
 * @Created by ASUS
 */
@Component
public class ConsumerDeadListener {


    @RabbitListener(queues = {"mq.message.dead.queue"})
    public void reviceDead(Message message, @Headers Map<String, Object> headers, Channel channel) {

        String value = new String(message.getBody());

        System.out.println("消费死信队列里的消息...."+value);

        Long deliveryTag  = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            channel.basicAck(deliveryTag ,false);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}