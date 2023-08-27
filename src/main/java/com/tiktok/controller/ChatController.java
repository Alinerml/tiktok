package com.tiktok.controller;

import com.tiktok.common.springbootwebsocketdemo.model.ChatMessage;
import com.tiktok.common.springbootwebsocketdemo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Value("${spring.redis.channel.msgToAll}")
    private String msgToAll;

    @Value("${spring.redis.set.onlineUsers}")
    private String onlineUsers;

    @Value("${spring.redis.channel.userStatus}")
    private String userStatus;

    @Qualifier("redisTemplate0")//因为security的项目中有两个redis库
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送消息
     * @param chatMessage
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        try {
            //将 消息 发送到 指定的频道,从而所有订阅该频道的客户端都可以收到该消息，实现了用户状态的广播功能
            redisTemplate.convertAndSend(msgToAll, JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 将用户添加到聊天室，并进行相应的处理
     * @param chatMessage
     * @param headerAccessor
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

        LOGGER.info("User added in Chatroom:" + chatMessage.getSender());
        try {
            //将发送消息的用户的用户名（存储在chatMessage对象的sender属性中）添加到WebSocket会话的属性中。
            // 这样，在后续的聊天过程中，可以通过会话属性来识别用户的身份。
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
            // 将用户添加到 onlineUsers 这个集合中，可以用于记录当前在线的用户列表
            redisTemplate.opsForSet().add(onlineUsers, chatMessage.getSender());
            //将 chatMessage 对象转换为 JSON 格式，并通过 Redis 的发布-订阅机制将消息发布到指定的频道
            redisTemplate.convertAndSend(userStatus, JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
