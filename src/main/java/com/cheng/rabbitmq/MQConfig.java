package com.cheng.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 19:59 2018/7/11
 * @Reference:
 */
@Configuration
public class MQConfig {
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUQ1 = "topic.queue1";
    public static final String TOPIC_QUEUQ2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.#";
    public static final String HEADER_QUEUE = "header.queue";
    public static final String FANOUT_EXCHANGE = "fanoutxchage";
    public static final String HEADERS_EXCHANGE = "headersExchage";

    /**
     * direct 模式：  交换机-exchange
     * @return
     */
    @Bean
    public Queue queue() {

        return new Queue(QUEUE, true);
    }

    /**
     * topic模式
     */
    @Bean
    public Queue topicQueue1() {

        return new Queue(TOPIC_QUEUQ1, true);
    }

    @Bean
    public Queue topicQueue2() {

        return new Queue(TOPIC_QUEUQ2, true);
    }

    @Bean
    public TopicExchange topicExchange() {

        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
    }

    /**
     * 广播模式 fanout
     */

    @Bean
    public FanoutExchange FanOutEx() {

        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding FanOutBinding1() {

        return BindingBuilder.bind(topicQueue1()).to(FanOutEx());
    }

    @Bean
    public Binding FanOutBinding2() {

        return BindingBuilder.bind(topicQueue2()).to(FanOutEx());
    }

    /**
     * header 模式
     */
    @Bean
    public HeadersExchange  headersExchange() {

        return new HeadersExchange(MQConfig.HEADERS_EXCHANGE);
    }

    @Bean
    public Queue headerQueue1() {
        return new Queue(HEADER_QUEUE, true);
    }
    @Bean
    public Binding headerBinding() {

        Map<String, Object> map = new HashMap<>();
        map.put("header1", "values1");
        map.put("header2", "values2");
        return BindingBuilder.bind(headerQueue1()).to(headersExchange()).whereAll(map).match();
    }

}