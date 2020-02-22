package com.tang.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MqConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/28 8:37
 * @Created by ASUS
 */
@Configuration
public class MqTopicConfig {

    // 消息 topic 方式
    private final static String MQ_MESSAGE_QUEUE = "mq.message.queue";
    private final static String MQ_MESSAGE_EXCHANGE = "mq.message.exchange";
    private final static String MQ_MESSAGE_ROUTINGKEY = "mq.*";


    // 定义死信队列
    private final static String MQ_MESSAGE_DEAD_QUEUE = "mq.message.dead.queue";
    private final static String MQ_MESSAGE_DEAD_EXCHANGE = "mq.message.dead.exchange";
    private final static String MQ_MESSAGE_DEAD_ROUTINGKEY = "mq.dead.message";

    /**
     * 死信队列 交换机标识符
     */
    private static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";


    /**
     * MethodName topicQueue
     * Description [ 定义队列 ]
     * Date 2019/12/28 9:16
     * Param []
     * return
     **/
    @Bean
    public Queue messageTopicQueue() {

        // 普通队列绑定到死信队列交换机上
        Map<String, Object> map = new HashMap<>();

        // 绑定死信队列的交换机
        map.put(DEAD_LETTER_QUEUE_KEY,MQ_MESSAGE_DEAD_EXCHANGE);
        // 绑定死信队列的路由key
        map.put(DEAD_LETTER_ROUTING_KEY, MQ_MESSAGE_DEAD_ROUTINGKEY);

        return new Queue(MQ_MESSAGE_QUEUE, true, false, false, map);
    }

    /**
     * MethodName messageExchange
     * Description [ 定义交换机 Exchange 为接口 ]
     * Date 2019/12/28 9:16
     * Param []
     * return
     **/
    @Bean
    public TopicExchange messageTopicExchange() {
        return new TopicExchange(MQ_MESSAGE_EXCHANGE,true,false);
    }

    /**
     * MethodName bindMessageExchange
     * Description [ 确定绑定关系 ]
     * Date 2019/12/28 9:16
     * Param [topicQueue, messageExchange]
     * return
     **/
    @Bean
    public Binding bindMessageExchange(Queue messageTopicQueue,TopicExchange messageTopicExchange) {

        return BindingBuilder.bind(messageTopicQueue).to(messageTopicExchange).with(MQ_MESSAGE_ROUTINGKEY);

    }

    /**
     * MethodName deadQueue
     * Description [ 死信队列名 ]
     * Date 2019/12/28 9:18
     * Param []
     * return
     **/
    @Bean
    public Queue messageDeadQueue() {
        return new Queue(MQ_MESSAGE_DEAD_QUEUE,true);
    }

    /**
     * MethodName deadDirectExchange
     * Description [ 死信交换机名 ]
     * Date 2019/12/28 9:19
     * Param []
     * return
     **/
    @Bean
    public DirectExchange messageDeadDirectExchange() {
        return new DirectExchange(MQ_MESSAGE_DEAD_EXCHANGE,true,false);
    }

    /**
     * MethodName bindMessageExchange
     * Description [ 死信绑定关系 ]
     * Date 2019/12/28 9:23
     * Param [deadQueue, deadTopicExchange]
     * return
     **/
    @Bean
    public Binding bindDeadMessageExchange(Queue messageDeadQueue,DirectExchange messageDeadDirectExchange) {

        return BindingBuilder.bind(messageDeadQueue).to(messageDeadDirectExchange).with(MQ_MESSAGE_DEAD_ROUTINGKEY);

    }


}