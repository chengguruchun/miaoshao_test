package com.cheng.rabbitmq;

import com.cheng.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 19:58 2018/7/11
 * @Reference:
 */
@Service
public class MQReceiver {
    private static final Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(Object message) {
        log.info("recive message:  " + message);
        //String msg = RedisService.beanToString()
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUQ1)
    public void receiveTopic1(Object message) {
        log.info("topic queue1 message:  " + message);
        //String msg = RedisService.beanToString()
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUQ2)
    public void receiveTopic2(Object message) {
        log.info("topic queue2 message:  " + message);
        //String msg = RedisService.beanToString()
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
    public void receiveHeader(byte[] message) {
        log.info("header queue message:  " + new String(message));
        //String msg = RedisService.beanToString()
    }

}