package com.tiktok.common.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tiktok.bean.Comment;
import com.tiktok.bean.dto.CommentDto;
import com.tiktok.common.common.exception.TiktokException;
import com.tiktok.common.common.utils.RedisUtil_db0;
import com.tiktok.common.springbootwebsocketdemo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CommentProducer {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    @Autowired
    private  AmqpTemplate amqpTemplate;

    public void saveProducer(Comment comment) {
        try {
//            String message = serializeCommentDto(comment); //可以改为与redis交互
            String message = RedisUtil_db0.beanToString(comment);

            log.info("send message:" + message);

//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("localhost");
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            amqpTemplate.convertAndSend(MQConfig.COMMENT_QUEUE, message);

//            channel.close();
//            connection.close();
        } catch (Exception e) {
            throw new TiktokException(e);
        }
    }

}