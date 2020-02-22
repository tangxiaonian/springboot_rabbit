package com.tang;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Classname ProviderApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/25 18:38
 * @Created by ASUS
 */
@SpringBootApplication
public class ProviderApplication implements CommandLineRunner {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {

        SpringApplication.run(ProviderApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {

            String value = "hello world!";

            Message message = MessageBuilder
                    .withBody(value.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_BYTES)
                    .setContentEncoding("utf-8")
                    .setMessageId(UUID.randomUUID().toString()).build();

            rabbitTemplate.convertAndSend("mq.message.exchange", "mq.mail", message);

            System.in.read();

        }
    }
}