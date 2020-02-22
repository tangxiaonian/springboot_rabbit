package com.tang.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


/**
 * @Classname ConsumerListener
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/25 18:40
 * @Created by ASUS
 */
@Component
public class ConsumerListener {

    @RabbitListener(queues = {"mq.message.queue"})
    public void revice(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {

        String value = new String(message.getBody());

        System.out.println("接收到消息---->" + value);

        // 手动签收

        try {

            int i = 1 / 0;

            Long deliveryTag  = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);

            System.out.println( "手动签收...." + deliveryTag);

            channel.basicAck(deliveryTag ,false);

        } catch (Exception e) {

            System.out.println("拒绝消息....");

            //拒绝消费消息（丢失消息） 给死信队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);

        }

    }

}